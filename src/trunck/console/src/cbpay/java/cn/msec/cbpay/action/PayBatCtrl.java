package cn.msec.cbpay.action;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.msec.cbpay.bean.COL001Req;
import cn.msec.cbpay.bean.COL001Res;
import cn.msec.cbpay.bean.COL002Req;
import cn.msec.cbpay.bean.COL005Req;
import cn.msec.cbpay.entity.BMerchantOrder;
import cn.msec.cbpay.entity.BMerchantOrderKey;
import cn.msec.cbpay.entity.PayBatBean;
import cn.msec.cbpay.mapper.BMerchantOrderMapper;
import cn.msec.cbpay.util.ConsoleConstant;
import cn.msec.cbpay.util.TCPSender;
import cn.msec.cbpay.util.XmlReversalBean;
import cn.msec.rest.db.service.DataService;
import cn.msec.rest.utils.BeanFactory;
import cn.msec.rest.utils.DBBean;
import cn.msec.rest.utils.SerializerUtil;
import cn.msec.rest.web.action.BasicCtrl;
import cn.msec.rest.web.bean.DbCondi;
import cn.msec.rest.web.bean.FieldsMapperBean;
import cn.msec.rest.web.bean.PageInfo;
import cn.msec.rest.web.bean.QueryMapperBean;
import cn.msec.rest.web.bean.SqlMaker;
import cn.msec.rest.web.bind.RequestJsonParam;

@Controller
@RequestMapping("/paybat")
public class PayBatCtrl extends BasicCtrl {

	private static BMerchantOrderMapper bMerchantOrderMapper = 
			(BMerchantOrderMapper)BeanFactory.getBean("bMerchantOrderMapper");
	
	private static DataService mysqlDataService = 
			(DataService)BeanFactory.getBean("mysqlDataService");
	Logger log = Logger.getLogger(PayBatCtrl.class);	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/makeDetail", method = RequestMethod.GET)
	@ResponseBody
	public Object get(@RequestJsonParam(value = "query",required=false) QueryMapperBean info,
			@RequestJsonParam(value = "fields",required=false) FieldsMapperBean fmb,
			PageInfo para, HttpServletRequest req) {
		int totalCount = 0;
		List<HashMap> list = null;
		PayBatBean payBatBean = new PayBatBean();
		try {
			DbCondi dc = new DbCondi();
			dc.setEntityClass(BMerchantOrder.class);
			dc.setKeyClass(BMerchantOrderKey.class);
			dc.setMapperClass(BMerchantOrderMapper.class);
			String json = "{\"$and\":[{\"buybatstatus\":\"06\"},{\"mountchangestatus\":\"05\"},{\"paybatstatus\":\"00\"}]}";
			if(info==null){
				info = new QueryMapperBean();
			}else{
				json = "{\"$and\":["+info.getNode().toString()+","+json+"]}";
			}
			ObjectMapper mapper = new ObjectMapper(); 
			JsonNode rootNode = mapper.readTree(json);
			info.setNode(rootNode);
			dc.setQmb(info);
			dc.setPageinfo(para);
			dc.setFmb(fmb);
			this.setTableName(dc);
			String sql = SqlMaker.getReferenceData(dc); 
			log.info("sql query["+sql+"]");
			list = SerializerUtil.deserializeArray(mysqlDataService.doBySQL(sql), HashMap.class);
			payBatBean.setOrderCount(list.size()+"");
			BigDecimal bg = new BigDecimal(0);
			for(HashMap map:list){
				bg = bg.add(new BigDecimal(map.get("purchaseamount").toString()));
			}
			payBatBean.setOrderMount(bg.toString());
			req.getSession().setAttribute("list", list);
			req.getSession().setAttribute("payBatBean", payBatBean);
		} catch (Exception e) {
			log.warn("PayBatCtrl get error..",e);
		}
		return payBatBean;
	}
	@ResponseBody
	@RequestMapping(value = "/sendCountChange", method = RequestMethod.POST)
	public String sendCountChange(HttpServletRequest req,String remark) throws InstantiationException, IllegalAccessException {
		log.info("sendCountChange is begin");
		log.info("col005 is begin");
		TCPSender sender = TCPSender.getInstance();
		/**
		 * 依次进行col005,col001，col002交易
		 */
		try {
			COL005Req col005Req = new COL005Req();
			List<HashMap> list = (List<HashMap>)req.getSession().getAttribute("list");
			log.info("remark["+remark+"]");
			if(StringUtils.isEmpty(remark)||list==null||list.size()==0){
				log.error("buy exg order data is null");
				return null;
			}
			col005Req.getHead().setTranCode(ConsoleConstant.COL005);
			col005Req.getBody().setTransType(ConsoleConstant.TRANSTYPE02);	//付汇前账户划账
			if(list!=null){
				log.info("order size ["+col005Req.getBody().getListSize()+"]");
				if(list.size()<999){
					col005Req.getBody().setListSize(list.size());
				}else{
					log.error("buy exg order number is much to more");
					return "订单数据太多，请分批次购汇!";
				}
			}
			String merchantid = (String)list.get(0).get("merchantid");
			for(HashMap map:list){
				COL005Req.Body.OrderList order = new COL005Req.Body.OrderList();
				order.setMerchantId((String)map.get("merchantid"));
				order.setOrderDate((String)map.get("orderdate"));
				order.setOrderId((String)map.get("orderid"));
				col005Req.getBody().getOrderList().add(order);
			}
			String col005ReqString = XmlReversalBean.beanToXml(col005Req, COL005Req.class);
			log.info("col005Req String ["+col005ReqString+"]");
			byte[] msg = sender.sendMsg(col005ReqString.getBytes(ConsoleConstant.encoding));
			String col005RepString =  new String(msg,ConsoleConstant.encoding);
			log.info("col005Rep String ["+col005RepString+"]");
			COL005Req col005rep = XmlReversalBean.xmlToBean(col005RepString, COL005Req.class);
			if(ConsoleConstant.RESPCODE_SUCC.equals(col005rep.getHead().getRespcode())){
				//如果账户划账成功，发起COL001交易
				log.info("col005 is succ");
				log.info("col001 is begin");
				COL001Req col001Req = new COL001Req();
				col001Req.getHead().setTranCode(ConsoleConstant.COL001);
				col001Req.getBody().setListSize(list.size());
				col001Req.getBody().setREMARK(remark);
				col001Req.getBody().setMerchantId(merchantid);
				for(HashMap map:list){
					COL001Req.Body.OrderList order = new COL001Req.Body.OrderList();
					order.setOrderDate((String)map.get("orderdate"));
					order.setOrderId((String)map.get("orderid"));
					col001Req.getBody().getPayBatList().add(order);
				}
				String col001ReqString = XmlReversalBean.beanToXml(col001Req, COL001Req.class);
				log.info("col001 Req String ["+col001ReqString+"]");
				msg = sender.sendMsg(col001ReqString.getBytes(ConsoleConstant.encoding));
				String col001RepString =  new String(msg,ConsoleConstant.encoding);
				log.info("col001 res String ["+col001RepString+"]");
				COL001Res col001rep = XmlReversalBean.xmlToBean(col001RepString, COL001Res.class);
				if(ConsoleConstant.RESPCODE_SUCC.equals(col001rep.getHead().getRespcode())){
					log.info("批量付汇成功 col001 is succ");
					log.info("上传文件col002 is begin");
					COL002Req col002Req = new COL002Req();
					col002Req.getHead().setTranCode(ConsoleConstant.COL002);
					col002Req.getBody().setFileDate(col001rep.getBody().getFileDate());
					col002Req.getBody().setFileName(col001rep.getBody().getFileName());
					col002Req.getBody().setTradeSn(col001rep.getBody().getTradeSn());
					String col002ReqString = XmlReversalBean.beanToXml(col002Req, COL002Req.class);
					log.info("col002 Req String ["+col002ReqString+"]");
					msg = sender.sendMsg(col002ReqString.getBytes(ConsoleConstant.encoding));
					String col002RepString =  new String(msg,ConsoleConstant.encoding);
					log.info("col002 res String ["+col002RepString+"]");
					COL002Req col002rep = XmlReversalBean.xmlToBean(col002RepString, COL002Req.class);
					if(ConsoleConstant.RESPCODE_SUCC.equals(col002rep.getHead().getRespcode())){
						log.info("col002 is end");
						log.info("上传文件成功 is succ");
						return "succ";
					}else{
						log.error("col002 is error");
						return "col002error";
					}
				}else{
					log.error("col001 is error");
					return "col001error";
				}
			}else{
				log.error("col005 is error");
				return "col005error";
			}
		} catch (Exception e) {
			log.error("PayBatCtrl get error..",e);
			return "syserror";
		}
	}

	private void setTableName(DbCondi dc){
		String tName = DBBean.getTableName2Class(BMerchantOrder.class);
		if(dc.getOther() == null){
			Map<String,Object> o = new HashMap<String,Object>();
			o.put(SqlMaker.TABLE_NAME, tName);
			dc.setOther(o);
		}else{
			dc.getOther().put(SqlMaker.TABLE_NAME, tName);
		}
	}
	
	@SuppressWarnings("serial")
	public static class BMerchantOrders extends ArrayList<BMerchantOrder> {  
	    public BMerchantOrders() { super(); }  
	}
}