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

import cn.msec.cbpay.bean.COL005Req;
import cn.msec.cbpay.bean.COL006Req;
import cn.msec.cbpay.bean.Head;
import cn.msec.cbpay.entity.BMerchantOrder;
import cn.msec.cbpay.entity.BMerchantOrderKey;
import cn.msec.cbpay.entity.BuyBatBean;
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
@RequestMapping("/buybat")
public class buyBatCtrl extends BasicCtrl {

	private static BMerchantOrderMapper bMerchantOrderMapper = 
			(BMerchantOrderMapper)BeanFactory.getBean("bMerchantOrderMapper");
	
	private static DataService mysqlDataService = 
			(DataService)BeanFactory.getBean("mysqlDataService");
	Logger log = Logger.getLogger(buyBatCtrl.class);	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/queryExceptionOrder")
	@ResponseBody
	public Object get(@RequestJsonParam(value = "query",required=false) QueryMapperBean info,
			@RequestJsonParam(value = "fields",required=false) FieldsMapperBean fmb,
			PageInfo para, HttpServletRequest req) {
		int totalCount = 0;
		List<HashMap> list = null;
		List<BuyBatBean> buyBatBeanlist = new ArrayList<BuyBatBean>(3);
		buyBatBeanlist.add(new BuyBatBean());
		buyBatBeanlist.add(new BuyBatBean());
		buyBatBeanlist.add(new BuyBatBean());
		try {
			DbCondi dc = new DbCondi();
			dc.setEntityClass(BMerchantOrder.class);
			dc.setKeyClass(BMerchantOrderKey.class);
			dc.setMapperClass(BMerchantOrderMapper.class);
//			String json = "{\"$or\":[{\"$and\":[{\"buybatstatus\":\"00\"},{\"mountchangestatus\":\"02\"},{\"orderstatus\":\"01\"}]},{\"$and\":[{\"orderdate\":{\"$lt\":\""+TimeUtil.getLastDate()+"\",\"$options\":\"i\"}},{\"buybatstatus\":\"00\"},{\"mountchangestatus\":\"00\"},{\"orderstatus\":\"01\"}]}]}";
//			String json = "{\"$or\":[{\"$and\":[{\"buybatstatus\":\"00\"},{\"mountchangestatus\":\"02\"},{\"orderstatus\":\"01\"}]},{\"buybatstatus\":\"00\"},{\"mountchangestatus\":\"00\"},{\"orderstatus\":\"01\"}]}]}";
			//1.未购汇,未划转成功  2.购汇前划转成功，未购汇或失败 3.购汇成功，未购汇后划转或失败
			/**
			 * SELECT *
			  FROM B_MERCHANT_ORDER
			 WHERE ((MERCHANTID like '%CF3000036439%') and (ORDERDATE = '20160616'))
			   and (((BUYBATSTATUS = '00') and (ORDERSTATUS = '01') and (MOUNTCHANGESTATUS != '02')) --正常流程
			   or
			       ((BUYBATSTATUS = '00' or BUYBATSTATUS = '05') and (MOUNTCHANGESTATUS = '02')) --购汇前划转成功
			   or
			       (((MOUNTCHANGESTATUS = '02') or (MOUNTCHANGESTATUS = '06')) and (BUYBATSTATUS = '06'))) --购汇后划转失败
			 */
			String json = "{\"$or\":[{\"$and\":[{\"mountchangestatus\":{\"$ne\":\"02\"}},{\"buybatstatus\":\"00\"},{\"orderstatus\":\"01\"}]},{\"$and\":[{\"$or\":[{\"buybatstatus\":\"00\"},{\"buybatstatus\":\"05\"}]},{\"mountchangestatus\":\"02\"}]},{\"$and\":[{\"$or\":[{\"mountchangestatus\":\"02\"},{\"mountchangestatus\":\"06\"}]},{\"buybatstatus\":\"06\"}]}]}";
			if(info==null){
				info = new QueryMapperBean();
			}else if(!"{}".equals(info.getNode().toString())){
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
			if(list!=null){
				String currency = null;
				String merchantid = null;
				int batnotchangecount = 0;
				int changenotbatcount = 0;
				int notbatnotchangecount = 0;
				BigDecimal batnotchangemount = new BigDecimal(0);
				BigDecimal changenotbatmount = new BigDecimal(0);
				BigDecimal notbatnotchangemount = new BigDecimal(0);
				for(HashMap map:list){
					if("00".equals(map.get("buybatstatus"))&&!"02".equals(map.get("mountchangestatus"))){
						notbatnotchangemount = notbatnotchangemount.add(new BigDecimal(map.get("purchaseamount").toString()));
						notbatnotchangecount++;
					}else if("02".equals(map.get("mountchangestatus"))&&("00".equals(map.get("buybatstatus"))||"05".equals(map.get("buybatstatus")))){
						changenotbatmount = changenotbatmount.add(new BigDecimal(map.get("purchaseamount").toString()));
						changenotbatcount++;
					}else{
						batnotchangemount = batnotchangemount.add(new BigDecimal(map.get("purchaseamount").toString()));
						batnotchangecount++;
					}
					if(StringUtils.isEmpty(merchantid)){
						merchantid = (String)map.get("merchantid");
						currency = (String)map.get("currency");
					}
				}
				buyBatBeanlist.get(0).setCurrency(currency);
				buyBatBeanlist.get(1).setCurrency(currency);
				buyBatBeanlist.get(2).setCurrency(currency);
				buyBatBeanlist.get(0).setMerchantid(merchantid);
				buyBatBeanlist.get(1).setMerchantid(merchantid);
				buyBatBeanlist.get(2).setMerchantid(merchantid);
				buyBatBeanlist.get(0).setOrderMount(notbatnotchangemount.toString());
				buyBatBeanlist.get(1).setOrderMount(changenotbatmount.toString());
				buyBatBeanlist.get(2).setOrderMount(batnotchangemount.toString());
				buyBatBeanlist.get(0).setOrderCount(notbatnotchangecount+"");
				buyBatBeanlist.get(1).setOrderCount(changenotbatcount+"");
				buyBatBeanlist.get(2).setOrderCount(batnotchangecount+"");
				
				req.getSession().setAttribute("buybatlist", list);
			}
			
		} catch (Exception e) {
			log.warn("PayBatCtrl get error..",e);
		}
		return buyBatBeanlist;
	}
	@ResponseBody
	@RequestMapping(value = "/sendtobuy", method = RequestMethod.POST)
	public String sendCountChange(HttpServletRequest req,String cause) throws InstantiationException, IllegalAccessException {
		log.info("sendtobuy is begin");
		TCPSender sender = TCPSender.getInstance();
		String result = "succ";
		if(!"2".equals(cause)&&!"1".equals(cause)&&!"0".equals(cause)){
			log.error("data is error");
			return "dataerror";
		}
		try {
			List<HashMap> list = (List<HashMap>)req.getSession().getAttribute("buybatlist");
			String TRANS_TYPE_BEFORE_FEP = "00";//购汇前  FEP即：Foreign Exchange Purchase
			String TRANS_TYPE_AFTER_FEP = "01";//购汇后
			if("0".equals(cause)){//购汇前划转未成功
				List<HashMap> listtmp = new ArrayList<HashMap>();
				for(HashMap map :list){
					if("00".equals(map.get("buybatstatus"))&&!"02".equals(map.get("mountchangestatus"))){
						listtmp.add(map);
					}
				}
				if(listtmp.size()>0){
					result = sendcol005(listtmp,sender,cause,TRANS_TYPE_BEFORE_FEP);
					if("succ".equals(result)){
						result = sendcol006(listtmp, sender, cause);
					}
				}
			}else if("1".equals(cause)){//购汇未成功
				List<HashMap> listtmp = new ArrayList<HashMap>();
				for(HashMap map :list){
					if("02".equals(map.get("mountchangestatus"))&&("00".equals(map.get("buybatstatus"))||"05".equals(map.get("buybatstatus")))){
						listtmp.add(map);
					}
				}
				if(listtmp.size()>0){
					result = sendcol006(listtmp, sender, cause);
				}
			}else{//购汇后划转未成功
				List<HashMap> listtmp = new ArrayList<HashMap>();
				for(HashMap map :list){
					if("06".equals(map.get("buybatstatus"))){
						listtmp.add(map);
					}
				}
				if(listtmp.size()>0){
					result = sendcol005(listtmp,sender,cause,TRANS_TYPE_AFTER_FEP);
				}
			}
		} catch (Exception e) {
			log.error("buyBatCtrl get error..",e);
			return "syserror";
		}
		log.info("sendtobuy is succ");
		return result;
	}
	/**
	 * 组装col006批量购汇报文
	 */
	private String sendcol006(List<HashMap> list,TCPSender sender,String cause) throws Exception{
		log.info("col006 is begin");
		int count = 0;
		String currency_type = null;
		String merchantId = null;
		COL006Req col006Req = new COL006Req();
		Head head = new Head();
		head.setTranCode("col006");
		head.setFlowno(merchantId+"---"+cause);
		col006Req.setHead(head);
		
		COL006Req.Body ctb10body = new COL006Req.Body();
		List<COL006Req.Body.BuyBatList> buybatList = new ArrayList<COL006Req.Body.BuyBatList>();
		count = 0;
		for(HashMap map:list){
			COL006Req.Body.BuyBatList order = new COL006Req.Body.BuyBatList();
			order.setOrderDate((String)map.get("orderdate"));
			order.setOrderId((String)map.get("orderid"));
			buybatList.add(order);
			if(count==0){
				currency_type = (String)map.get("currency");
				merchantId = (String)map.get("merchantid");
			}
			count++;
		}
		ctb10body.setListSize(count);
		ctb10body.setCurrencyCode(currency_type);
		ctb10body.setMerchantId(merchantId);
		head.setFlowno(merchantId+"---"+cause);
		ctb10body.setBuyBatList(buybatList);
		col006Req.setBody(ctb10body);
		
		String reqMsg = XmlReversalBean.beanToXml(col006Req, COL006Req.class);
		//调用批量购汇
		
		log.info("col006 reqMsg["+reqMsg+"]");
		byte[] repmsg = sender.sendMsg(reqMsg.getBytes(ConsoleConstant.encoding));
		reqMsg = new String(repmsg,ConsoleConstant.encoding);
		log.info("col006 repMsg["+reqMsg+"]");
		col006Req = XmlReversalBean.xmlToBean(reqMsg, COL006Req.class);
		String resCode = col006Req.getHead().getRespcode();
		if(!resCode.equals("CBP000")){
			log.error("response code is ["+col006Req.getHead().getRespcode()+"] response msg is ["+col006Req.getHead().getRespmsg()+"]");
			return "col006error";
		}
		log.info("col006 is succ");
		return "succ";
	}
	/**
	 * 组装发送05报文
	 */
	private String sendcol005(List<HashMap> list,TCPSender sender,String cause,String transtype) throws Exception{

		log.info("col005 is begin");
		int count = 0;
		String currency_type = null;
		String merchantId = null;
		COL005Req col005Req = new COL005Req();
		Head head = new Head();
		head.setTranCode("col005");
		col005Req.setHead(head);
		
		BigDecimal midstatusmount = new BigDecimal(0);
		COL005Req.Body body = new COL005Req.Body();
		body.setTransType(transtype);
		List<COL005Req.Body.OrderList> orderList = new ArrayList<COL005Req.Body.OrderList>();
		for(HashMap map:list){
			COL005Req.Body.OrderList order = new COL005Req.Body.OrderList();
			order.setMerchantId((String)map.get("merchantid"));
			order.setOrderDate((String)map.get("orderdate"));
			order.setOrderId((String)map.get("orderid"));
			if(count==0){
				currency_type = (String)map.get("currency");
				merchantId = (String)map.get("merchantid");
			}
			orderList.add(order);
			count++;
		}
		head.setFlowno(merchantId+"---"+cause);
		body.setListSize(count);
		body.setOrderList(orderList);
		col005Req.setBody(body);
		
		String reqMsg = XmlReversalBean.beanToXml(col005Req, COL005Req.class);
		//调用账户划转
		log.info("col005 reqMsg["+reqMsg+"]");
		byte[] repmsg = sender.sendMsg(reqMsg.getBytes(ConsoleConstant.encoding));
		reqMsg = new String(repmsg,ConsoleConstant.encoding);
		log.info("col005 repMsg["+reqMsg+"]");
		col005Req = XmlReversalBean.xmlToBean(reqMsg, COL005Req.class);
		String resCode = col005Req.getHead().getRespcode();
		
		if(!resCode.equals("CBP000")){
			log.error("response code is ["+col005Req.getHead().getRespcode()+"] response msg is ["+col005Req.getHead().getRespmsg()+"]");
			return "col005error";
		}
		log.info("col005 is succ");
		return "succ";
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