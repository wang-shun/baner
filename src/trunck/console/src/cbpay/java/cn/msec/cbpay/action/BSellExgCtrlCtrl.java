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

import cn.msec.cbpay.bean.COL008Req;
import cn.msec.cbpay.bean.Head;
import cn.msec.cbpay.entity.BSellExgCtrl;
import cn.msec.cbpay.entity.BSellExgCtrlExample;
import cn.msec.cbpay.entity.BSellExgCtrlKey;
import cn.msec.cbpay.mapper.BSellExgCtrlMapper;
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
@RequestMapping("/bsellexgctrl")
public class BSellExgCtrlCtrl extends BasicCtrl {

	private static BSellExgCtrlMapper bSellExgCtrlMapper = 
			(BSellExgCtrlMapper)BeanFactory.getBean("bSellExgCtrlMapper");
	
	private static DataService mysqlDataService = 
			(DataService)BeanFactory.getBean("mysqlDataService");
	
	/**
	 * ajax单条数据插入 
	 * url:'http://ip/rest/bsellexgctrl' 
	 * data:'{"key1":"value1","key2":"value2",...}' 
	 * type:’POST
	 */
	@RequestMapping(value="",method=RequestMethod.POST)
	@ResponseBody
	public ReturnInfo insert( @RequestBody BSellExgCtrl info,HttpServletRequest req) {
		try {
			bSellExgCtrlMapper.insert(info);
			return ReturnInfo.Success;
		} catch (Exception e) {
			log.warn("BSellExgCtrlCtrl insert error..",e);
//			e.printStackTrace();
		}
		return ReturnInfo.Faild;
	}
	
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "loadinit", method = RequestMethod.GET)
	@ResponseBody
	public Object get(@RequestJsonParam(value = "query",required=false) QueryMapperBean info,
			@RequestJsonParam(value = "fields",required=false) FieldsMapperBean fmb,
			PageInfo para, HttpServletRequest req) {
		int totalCount = 0;
		List<HashMap> list = null;
		try {
			DbCondi dc = new DbCondi();
			dc.setEntityClass(BSellExgCtrl.class);
			dc.setKeyClass(BSellExgCtrlKey.class);
			dc.setMapperClass(BSellExgCtrlMapper.class);
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
				map.put(KeyExplainHandler.ID_KEY,"paybatno-"+map.get("paybatno")+"_paydate-"+map.get("paydate")+"_totNum-"+map.get("totNum")+"_processStatus-"+map.get("processStatus"));
			}
		} catch (Exception e) {
			log.warn("BSellExgCtrlCtrl get error..",e);
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
						log.debug("checkobj["+checkobj+"]");
						String[] checkobjattrs = checkobj.split("_");
						COL008Req col008Req = new COL008Req();
						COL008Req.Body body = new COL008Req.Body();
						boolean flag = true;//用来判断是否最终状态，默认不是最终状态。
						for(String checkobjvalues:checkobjattrs){
							String[] checkobjattr = checkobjvalues.split("-");
							if("paybatno".equals(checkobjattr[0])){
								body.setSEQ_NO(checkobjattr[1]);
							}
							if("paydate".equals(checkobjattr[0])){
								body.setPaydate(checkobjattr[1]);
							}
							if("totNum".equals(checkobjattr[0])){
								short totnum = Short.parseShort(checkobjattr[1]);
								body.setTotalCount(totnum);
							}
							if("processStatus".equals(checkobjattr[0])){
								if("T".equals(checkobjattr[1])||"F".equals(checkobjattr[1]))
									flag = false;//是最终状态
							}
						}
						if(flag){
							col008Req.setBody(body);
							Head head = new Head();
							head.setTranCode("col008");
							col008Req.setHead(head);
							String col008ReqString = XmlReversalBean.beanToXml(col008Req, COL008Req.class);
							log.debug("col008req["+col008ReqString+"]");
							byte[] msg = sender.sendMsg(col008ReqString.getBytes(ConsoleConstant.encoding));
							String msgstring = new String(msg,ConsoleConstant.encoding);
							log.debug("col008res["+msgstring+"]");
							col008Req = XmlReversalBean.xmlToBean(msgstring, COL008Req.class);
							if(!"CBP000".equals(col008Req.getHead().getRespcode())){
								log.error("respcode["+col008Req.getHead().getRespcode()+"]");
								return col008Req.getHead().getRespmsg();
							}
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
	 * ajax根据ID主键查询
	 * 请求 url: 'http://ip/app/bsellexgctrl/(_id值)' 
	 * dataType: 'json' 
	 * type: 'get'
	 */
	@RequestMapping(value="/{key:.*}",method=RequestMethod.GET)
	@ResponseBody
	public ListInfo<BSellExgCtrl> get(@PathVariable String key,HttpServletRequest req) {
		int totalCount = 1;
		List<BSellExgCtrl> list = null;
		try {
			BSellExgCtrl akey = new BSellExgCtrl();
				
				Field keyField=FieldUtils.allDeclaredField(BSellExgCtrlKey.class).get(0);

				if(keyField.getType().isInstance(1)){
					FieldUtils.setObjectValue(akey, keyField, Integer.parseInt(key));
				}else if(keyField.getType().isInstance(1L)){
					FieldUtils.setObjectValue(akey, keyField, Long.parseLong(key));
				}else{
					FieldUtils.setObjectValue(akey, keyField, key);
				}				
			
			if(true && akey.getPaybatno() == null  && akey.getPaydate() == null ){
				list = new ArrayList<BSellExgCtrl>();
			}else{
				BSellExgCtrlExample example = new BSellExgCtrlExample();
				example.createCriteria().andPaybatnoEqualTo(akey.getPaybatno()).andPaydateEqualTo(akey.getPaydate());
				list = bSellExgCtrlMapper.selectByExample(example);
			}
			totalCount = list.size();
		} catch (Exception e) {
			log.warn("BSellExgCtrlCtrl get by key error..",e);
		}
		return  new ListInfo<>(totalCount, list, 0, 1);
	}
	
	/**
	 * ajax根据主键删除 
	 * url:'http://ip/app/bsellexgctrl/(_id值)' 
	 * type: 'DELETE' 
	 * dataType: 'json' 
	 */
	@RequestMapping(value="/{key:.*}",method=RequestMethod.DELETE)
	@ResponseBody
	public ReturnInfo delete(@PathVariable String key,HttpServletRequest req) {
		try {
			BSellExgCtrl akey = new BSellExgCtrl();
				
				Field keyField=FieldUtils.allDeclaredField(BSellExgCtrlKey.class).get(0);

				if(keyField.getType().isInstance(1)){
					FieldUtils.setObjectValue(akey, keyField, Integer.parseInt(key));
				}else if(keyField.getType().isInstance(1L)){
					FieldUtils.setObjectValue(akey, keyField, Long.parseLong(key));
				}else{
					FieldUtils.setObjectValue(akey, keyField, key);
				}
				
			if(true && akey.getPaybatno() != null  && akey.getPaydate() != null ){
				bSellExgCtrlMapper.deleteByPrimaryKey(akey);
				return ReturnInfo.Success;
			}
		} catch (Exception e) {
			log.warn("BSellExgCtrlCtrl delete by key error..");
		}
		return ReturnInfo.Faild;
	}
	
	/**
	 * ajax根据主键单条修改 
	 * url:'http://ip/app/bsellexgctrl/(_id值)' 
	 * data:'{"key1":"value1","key2":"value2",...}' 
	 * type:'PUT'
	 */
	@RequestMapping(value="/{key:.*}",method=RequestMethod.PUT)
	@ResponseBody
	public ReturnInfo update(@PathVariable String key,@RequestBody BSellExgCtrl info,HttpServletRequest req) {
		try {
			if(info!=null){
				BSellExgCtrl akey = new BSellExgCtrl();
				
				Field keyField=FieldUtils.allDeclaredField(BSellExgCtrlKey.class).get(0);

				if(keyField.getType().isInstance(1)){
					FieldUtils.setObjectValue(akey, keyField, Integer.parseInt(key));
				}else if(keyField.getType().isInstance(1L)){
					FieldUtils.setObjectValue(akey, keyField, Long.parseLong(key));
				}else{
					FieldUtils.setObjectValue(akey, keyField, key);
				}
				
				BSellExgCtrlExample example = new BSellExgCtrlExample();
				example.createCriteria().andPaybatnoEqualTo(akey.getPaybatno()).andPaydateEqualTo(akey.getPaydate());
				bSellExgCtrlMapper.updateByExampleSelective(info, example);
			}
			return ReturnInfo.Success;
		} catch (Exception e) {
			log.warn("BSellExgCtrl update by key error..");
		}
		return ReturnInfo.Faild;
	}
	
	private void setTableName(DbCondi dc){
		String tName = DBBean.getTableName2Class(BSellExgCtrl.class);
		if(dc.getOther() == null){
			Map<String,Object> o = new HashMap<String,Object>();
			o.put(SqlMaker.TABLE_NAME, tName);
			dc.setOther(o);
		}else{
			dc.getOther().put(SqlMaker.TABLE_NAME, tName);
		}
	}
	
	@SuppressWarnings("serial")
	public static class BSellExgCtrls extends ArrayList<BSellExgCtrl> {  
	    public BSellExgCtrls() { super(); }  
	}
}