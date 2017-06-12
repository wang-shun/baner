package cn.msec.cbpay.action;



import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.msec.cbpay.bean.COL005Req;
import cn.msec.cbpay.bean.COL005Res;
import cn.msec.cbpay.bean.COL007Req;
import cn.msec.cbpay.bean.COL007Res;
import cn.msec.cbpay.bean.Head;
import cn.msec.cbpay.entity.BBuyExgCtrl;
import cn.msec.cbpay.entity.BBuyExgCtrlExample;
import cn.msec.cbpay.entity.BBuyExgCtrlKey;
import cn.msec.cbpay.entity.BMerchantOrder;
import cn.msec.cbpay.entity.BMerchantOrderExample;
import cn.msec.cbpay.mapper.BBuyExgCtrlMapper;
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
import cn.msec.rest.web.bean.ListInfo;
import cn.msec.rest.web.bean.PageInfo;
import cn.msec.rest.web.bean.QueryMapperBean;
import cn.msec.rest.web.bean.ReturnInfo;
import cn.msec.rest.web.bean.SqlMaker;
import cn.msec.rest.web.bind.FieldUtils;
import cn.msec.rest.web.bind.KeyExplainHandler;
import cn.msec.rest.web.bind.RequestJsonParam;

@Slf4j
@Controller
@RequestMapping("/bbuyexgctrl")
public class BBuyExgCtrlCtrl extends BasicCtrl {

	private static BBuyExgCtrlMapper bBuyExgCtrlMapper = (BBuyExgCtrlMapper)BeanFactory.getBean("bBuyExgCtrlMapper");
	
	private static BMerchantOrderMapper bMerchantOrderMapper = (BMerchantOrderMapper)BeanFactory.getBean("bMerchantOrderMapper");
	
	private static DataService mysqlDataService = 
			(DataService)BeanFactory.getBean("mysqlDataService");
	
	/**
	 * ajax单条数据插入 
	 * url:'http://ip/rest/bbuyexgctrl' 
	 * data:'{"key1":"value1","key2":"value2",...}' 
	 * type:’POST
	 */
	@RequestMapping(value="",method=RequestMethod.POST)
	@ResponseBody
	public ReturnInfo insert( @RequestBody BBuyExgCtrl info,HttpServletRequest req) {
		try {
			bBuyExgCtrlMapper.insert(info);
			return ReturnInfo.Success;
		} catch (Exception e) {
			log.warn("BBuyExgCtrlCtrl insert error..",e);
//			e.printStackTrace();
		}
		return ReturnInfo.Faild;
	}
	
	/**
	 * ajax精确查询请求 
	 * url: 'http://ip/app/bbuyexgctrl/?query=({"key1":"value1","key2":"value2",...})' 
	 * dataType: 'json' 
	 * type: 'get'
	 * 
	 * ajax无条件查询全部请求
	 * url: 'http://ip/app/bbuyexgctrl' 
	 * dataType: 'json' 
	 * type: 'get' 
	 * 
	 * ajax模糊查询请求 
	 * url: 'http://ip/app/bbuyexgctrl/?query={"(colname)":{"$regex":"(colvalue)","$options":"i"} 
	 * dataType: 'json' 
	 * type: 'get'
	 * 
	 * ajax分页查询
	 * 请求 url: 'http://ip/app/bbuyexgctrl/?query=(空或{"key1":"value1","key2":"value2",...})&skip=" + beginRow + "&limit=" + rowNum' 
	 * dataType:'json' 
	 * type:'get' 
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="loadinit",method=RequestMethod.GET)
	@ResponseBody
	public Object get(@RequestJsonParam(value = "query",required=false) QueryMapperBean info,
			@RequestJsonParam(value = "fields",required=false) FieldsMapperBean fmb,
			PageInfo para, HttpServletRequest req) {
		int totalCount = 0;
		List<HashMap> list = null;
		try {
			DbCondi dc = new DbCondi();
			dc.setEntityClass(BBuyExgCtrl.class);
			dc.setKeyClass(BBuyExgCtrlKey.class);
			dc.setMapperClass(BBuyExgCtrlMapper.class);
			dc.setQmb(info);
			dc.setPageinfo(para);
			dc.setFmb(fmb);
			this.setTableName(dc);
			
			String sql = SqlMaker.getReferenceCountSql(dc);
			totalCount = mysqlDataService.getCount(sql);
			sql = SqlMaker.getReferenceData(dc); 
			list = SerializerUtil.deserializeArray(mysqlDataService.doBySQL(sql), HashMap.class);
			for(HashMap map : list){
				for(Field filed:FieldUtils.allDeclaredField(dc.getKeyClass())){
					if(map.get(filed.getName())==null){
						map.put(filed.getName(), "");
					}
				}
				map.put(KeyExplainHandler.ID_KEY, "buybatno-"+map.get("buybatno")+"_buydate-"+map.get("buydate")+"_totNum-"+map.get("totNum")+"_processStatus-"+map.get("processStatus"));
			}
		} catch (Exception e) {
			log.warn("BBuyExgCtrlCtrl get error..",e);
		}
		if(para.isPage()){
			return new ListInfo<>(totalCount, list, para);
		}else{
			return list;
		}
	}
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="syncstatus",method=RequestMethod.POST)
	@ResponseBody
	public String syncStatus(String checkValue){
		log.info("sync is begin ..");
		try{
			log.debug("checkValue["+checkValue+"]");
			String[] checkArray = checkValue.split(":");
			TCPSender sender = TCPSender.getInstance();
			if(null!=checkArray&&checkArray.length>0){
				log.info("checkValue length is "+checkArray.length);
				for(String checkobj:checkArray){
					if(StringUtils.isNotBlank(checkobj)){
						short totnum = 0;
						log.debug("checkobj["+checkobj+"]");
						String[] checkobjattrs = checkobj.split("_");
						COL007Req col007Req = new COL007Req();
						COL007Req.Body body = new COL007Req.Body();
						boolean flag = true;//用来判断是否最终状态，默认不是最终状态。
						for(String checkobjvalues:checkobjattrs){
							String[] checkobjattr = checkobjvalues.split("-");
							if("buybatno".equals(checkobjattr[0])){
								body.setSEQ_NO(checkobjattr[1]);
							}
							if("buydate".equals(checkobjattr[0])){
								body.setBUYDATE(checkobjattr[1]);
							}
							if("totNum".equals(checkobjattr[0])){
								totnum = Short.parseShort(checkobjattr[1]);
								body.setTotalCount(totnum);
							}
							if("processStatus".equals(checkobjattr[0])){
								if("T".equals(checkobjattr[1])||"F".equals(checkobjattr[1]))
									flag = false;//是最终状态
							}
						}
						
						if(!flag){
							log.info("交易已经为最终状态不需要同步状态");
							continue;
						}

						col007Req.setBody(body);
						Head head = new Head();
						head.setTranCode("col007");
						col007Req.setHead(head);
						String col007ReqString = XmlReversalBean.beanToXml(col007Req, COL007Req.class);
						log.debug("col007req["+col007ReqString+"]");
						byte[] msg = sender.sendMsg(col007ReqString.getBytes(ConsoleConstant.encoding));
						String msgstring = new String(msg,ConsoleConstant.encoding);
						log.debug("col007res["+msgstring+"]");
						COL007Res col007Res = XmlReversalBean.xmlToBean(msgstring, COL007Res.class);
						
						if(!"CBP000".equals(col007Res.getHead().getRespcode())){
							log.error("respcode["+col007Res.getHead().getRespcode()+"]");
							return col007Res.getHead().getRespmsg();
						}
						
						
						//如果购汇状态为T（交割成功）,发起购汇后账户划转
						if("CBP000".equals(col007Res.getHead().getRespcode()) && "T".equals(col007Res.getBody().getPROCESS_STATUS())){
							//发起购汇后账户划转交易
							log.info("col005 is begin");
							COL005Req col005Req = new COL005Req();
							Head col005head = new Head();
							col005head.setTranCode("col005");
							
							COL005Req.Body col005body = new COL005Req.Body();
							col005body.setTransType("01");
//							String sqlStatement = "select * from b_merchant_order t where t.buybatno='"++"' and t.buybatdate='"+body.getBUYDATE()+"'";
							BMerchantOrderExample example =  new BMerchantOrderExample();
							example.createCriteria()
							.andBuybatnoEqualTo(body.getSEQ_NO())
							.andBuybatdateEqualTo(body.getBUYDATE())
							//账户划转状态不能为05（购汇后账户划账成功）
							.andMountchangestatusNotEqualTo("05");
							
							//从数据库中查询当前购汇批次对应的全部交易
							List<BMerchantOrder> orderListdb = bMerchantOrderMapper.selectByExample(example);
							if(orderListdb==null || orderListdb.size()!=totnum){
								//如果当前批次账户划转
								log.error("交易状态错误，当前批次的购汇状态更新后，需要发起账户划账的交易数为["+totnum+"]条    查询到的数据为["+orderListdb.size()+"]");
								return "col005error";
							}
							
							List<COL005Req.Body.OrderList> orderList = new ArrayList<COL005Req.Body.OrderList>();
							int count=0;
							for (int i = 0; i < orderListdb.size(); i++) {
								BMerchantOrder bMerchantOrder = orderListdb.get(i);
								COL005Req.Body.OrderList order = new COL005Req.Body.OrderList();
								order.setMerchantId(bMerchantOrder.getMerchantid());
								order.setOrderDate(bMerchantOrder.getOrderdate());
								order.setOrderId(bMerchantOrder.getOrderid());
								
								orderList.add(order);
								count++;
							}
							col005body.setListSize(count);
							col005body.setOrderList(orderList);
							col005Req.setBody(col005body);
							col005Req.setHead(col005head);
							
							String reqMsg = XmlReversalBean.beanToXml(col005Req, COL005Req.class);
							//调用账户划转
							log.info("col005 reqMsg["+reqMsg+"]");
							byte[] repmsg = sender.sendMsg(reqMsg.getBytes(ConsoleConstant.encoding));
							reqMsg = new String(repmsg,ConsoleConstant.encoding);
							log.info("col005 repMsg["+reqMsg+"]");
							COL005Res col005Res = XmlReversalBean.xmlToBean(reqMsg, COL005Res.class);
							String resCode = col005Res.getHead().getRespcode();
							
							if(!resCode.equals("CBP000")){
								log.error("response code is ["+col005Req.getHead().getRespcode()+"] response msg is ["+col005Req.getHead().getRespmsg()+"]");
								return "col005error";
							}
							log.info("col005 is succ");
						}
					}
				}
			}
		}catch(Exception e){
			log.error("syncStatus is error",e);
			return "systemerror";
		}
		log.info("sync is succ");
		return "succ";
	}
	/**
	 * 没有实现不用关注
	 * @param key
	 * @param req
	 * @return
	 */
	@RequestMapping(value="/{key:.*}",method=RequestMethod.GET)
	@ResponseBody
	public ListInfo<BBuyExgCtrl> get(@PathVariable String key,HttpServletRequest req) {
		int totalCount = 1;
		List<BBuyExgCtrl> list = null;
		try {
			BBuyExgCtrl akey = new BBuyExgCtrl();
				
				Field keyField=FieldUtils.allDeclaredField(BBuyExgCtrlKey.class).get(0);

				if(keyField.getType().isInstance(1)){
					FieldUtils.setObjectValue(akey, keyField, Integer.parseInt(key));
				}else if(keyField.getType().isInstance(1L)){
					FieldUtils.setObjectValue(akey, keyField, Long.parseLong(key));
				}else{
					FieldUtils.setObjectValue(akey, keyField, key);
				}
				
			
			if(true && akey.getBuybatno() == null  && akey.getBuydate() == null ){
				list = new ArrayList<BBuyExgCtrl>();
			}else{
				BBuyExgCtrlExample example = new BBuyExgCtrlExample();
				example.createCriteria().andBuybatnoEqualTo(akey.getBuybatno()).andBuydateEqualTo(akey.getBuydate());
				list = bBuyExgCtrlMapper.selectByExample(example);
			}
			totalCount = list.size();
		} catch (Exception e) {
			log.warn("BBuyExgCtrlCtrl get by key error..",e);
		}
		return  new ListInfo<>(totalCount, list, 0, 1);
	}
	
	/**
	 * 没有实现不用关注
	 * @param key
	 * @param req
	 * @return
	 */
	@RequestMapping(value="/{key:.*}",method=RequestMethod.DELETE)
	@ResponseBody
	public ReturnInfo delete(@PathVariable String key,HttpServletRequest req) {
		try {
			BBuyExgCtrl akey = new BBuyExgCtrl();
				
				Field keyField=FieldUtils.allDeclaredField(BBuyExgCtrlKey.class).get(0);

				if(keyField.getType().isInstance(1)){
					FieldUtils.setObjectValue(akey, keyField, Integer.parseInt(key));
				}else if(keyField.getType().isInstance(1L)){
					FieldUtils.setObjectValue(akey, keyField, Long.parseLong(key));
				}else{
					FieldUtils.setObjectValue(akey, keyField, key);
				}
				
			if(true && akey.getBuybatno() != null  && akey.getBuydate() != null ){
				//bBuyExgCtrlMapper.deleteByPrimaryKey(akey);
				return ReturnInfo.Success;
			}
		} catch (Exception e) {
			log.warn("BBuyExgCtrlCtrl delete by key error..");
		}
		return ReturnInfo.Faild;
	}
	
	/**
	 * 没有实现不用关注
	 * @param key
	 * @param info
	 * @param req
	 * @return
	 */
	@RequestMapping(value="/{key:.*}",method=RequestMethod.PUT)
	@ResponseBody
	public ReturnInfo update(@PathVariable String key,@RequestBody BBuyExgCtrl info,HttpServletRequest req) {
		try {
			if(info!=null){
				BBuyExgCtrl akey = new BBuyExgCtrl();
				
				Field keyField=FieldUtils.allDeclaredField(BBuyExgCtrlKey.class).get(0);

				if(keyField.getType().isInstance(1)){
					FieldUtils.setObjectValue(akey, keyField, Integer.parseInt(key));
				}else if(keyField.getType().isInstance(1L)){
					FieldUtils.setObjectValue(akey, keyField, Long.parseLong(key));
				}else{
					FieldUtils.setObjectValue(akey, keyField, key);
				}
				
				if(keyField.getType().isInstance(1)){
					FieldUtils.setObjectValue(akey, keyField, Integer.parseInt(key));
				}else if(keyField.getType().isInstance(1L)){
					FieldUtils.setObjectValue(akey, keyField, Long.parseLong(key));
				}else{
					FieldUtils.setObjectValue(akey, keyField, key);
				}
				BBuyExgCtrlExample example = new BBuyExgCtrlExample();
				example.createCriteria().andBuybatnoEqualTo(akey.getBuybatno()).andBuydateEqualTo(akey.getBuydate());
				bBuyExgCtrlMapper.updateByExampleSelective(info, example);
			}
			return ReturnInfo.Success;
		} catch (Exception e) {
			log.warn("BBuyExgCtrl update by key error..");
		}
		return ReturnInfo.Faild;
	}
	
	private void setTableName(DbCondi dc){
		String tName = DBBean.getTableName2Class(BBuyExgCtrl.class);
		if(dc.getOther() == null){
			Map<String,Object> o = new HashMap<String,Object>();
			o.put(SqlMaker.TABLE_NAME, tName);
			dc.setOther(o);
		}else{
			dc.getOther().put(SqlMaker.TABLE_NAME, tName);
		}
	}
	
	@SuppressWarnings("serial")
	public static class BBuyExgCtrls extends ArrayList<BBuyExgCtrl> {  
	    public BBuyExgCtrls() { super(); }  
	}
}