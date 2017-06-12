package cn.msec.cbpay.action;



import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.msec.cbpay.entity.BCheckStatus;
import cn.msec.cbpay.entity.BCheckStatusExample;
import cn.msec.cbpay.entity.BCheckStatusKey;
import cn.msec.cbpay.mapper.BCheckStatusMapper;
import cn.msec.cbpay.util.ConsoleConstant;
import cn.msec.cbpay.util.TCPSender;
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

import com.msec.cbpay.message.Head;
import com.msec.cbpay.message.checking.RecheckingReq;
import com.ztkx.cbpay.container.frame.FlowNoGenerator;

@Slf4j
@Controller
@RequestMapping("/bcheckstatus")
public class BCheckStatusCtrl extends BasicCtrl {

	private static BCheckStatusMapper bCheckStatusMapper =  (BCheckStatusMapper)BeanFactory.getBean("bCheckStatusMapper");
	
	private static DataService mysqlDataService =  (DataService)BeanFactory.getBean("mysqlDataService");
	
	/**
	 * ajax单条数据插入 
	 * url:'http://ip/rest/bcheckstatus' 
	 * data:'{"key1":"value1","key2":"value2",...}' 
	 * type:’POST
	 */
	@RequestMapping(value="",method=RequestMethod.POST)
	@ResponseBody
	public ReturnInfo insert( @RequestBody BCheckStatus info,HttpServletRequest req) {
		try {
			bCheckStatusMapper.insert(info);
			return ReturnInfo.Success;
		} catch (Exception e) {
			log.warn("BCheckStatusCtrl insert error..",e);
//			e.printStackTrace();
		}
		return ReturnInfo.Faild;
	}
	
	/**
	 * ajax精确查询请求 
	 * url: 'http://ip/app/bcheckstatus/?query=({"key1":"value1","key2":"value2",...})' 
	 * dataType: 'json' 
	 * type: 'get'
	 * 
	 * ajax无条件查询全部请求
	 * url: 'http://ip/app/bcheckstatus' 
	 * dataType: 'json' 
	 * type: 'get' 
	 * 
	 * ajax模糊查询请求 
	 * url: 'http://ip/app/bcheckstatus/?query={"(colname)":{"$regex":"(colvalue)","$options":"i"} 
	 * dataType: 'json' 
	 * type: 'get'
	 * 
	 * ajax分页查询
	 * 请求 url: 'http://ip/app/bcheckstatus/?query=(空或{"key1":"value1","key2":"value2",...})&skip=" + beginRow + "&limit=" + rowNum' 
	 * dataType:'json' 
	 * type:'get' 
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "", method = RequestMethod.GET)
	@ResponseBody
	public Object get(@RequestJsonParam(value = "query",required=false) QueryMapperBean info,
			@RequestJsonParam(value = "fields",required=false) FieldsMapperBean fmb,
			PageInfo para, HttpServletRequest req) {
		int totalCount = 0;
		List<HashMap> list = null;
		try {
			DbCondi dc = new DbCondi();
			dc.setEntityClass(BCheckStatus.class);
			dc.setKeyClass(BCheckStatusKey.class);
			dc.setMapperClass(BCheckStatusMapper.class);
			dc.setQmb(info);
			// 设定排序,setSort设定排序的列，当需要设定正序或是倒叙排序时，setSortModed必须为true。
			// 为false时，程序无法解析，排序列会被设定为null即 order by null;
			para.setSort("TRANDATE desc");
			para.setSortModed(true);

			dc.setPageinfo(para);
			dc.setFmb(fmb);
			this.setTableName(dc);
			// TEST query
			// if(info!=null){
			// StringBuffer keyValues = new StringBuffer();
			// for(EqualBean b : info.getEqual()){
			// keyValues.append(b.getFieldName()).append(" = ").append(b.getValue()).append(",");
			// }
			// for(LikeBean b : info.getLikes()){
			// keyValues.append(b.getFieldName()).append(" like ").append(b.getRegex()).append(",");
			// }
			// for(OrBean b : info.getOr()){
			// keyValues.append(b.getFieldName()).append(" or ").append(b.getValue()).append(",");
			// }
			// for(ConditionBean b : info.getCondition()){
			// keyValues.append(b.getFieldName()).append(" ").append(b.getCondi())
			// .append(" ").append(b.getValue()).append(",");
			// }
			// log.debug("传递参数为="+keyValues.toString());
			// }
			// //////////////////
			String sql = SqlMaker.getReferenceCountSql(dc);
			totalCount = mysqlDataService.getCount(sql);
			sql = SqlMaker.getReferenceData(dc);
			list = SerializerUtil.deserializeArray(mysqlDataService.doBySQL(sql), HashMap.class);
			for (HashMap map : list) {
				for (Field filed : FieldUtils.allDeclaredField(dc.getKeyClass())) {
					if (map.get(filed.getName()) == null) {
						map.put(filed.getName(), "");
					}
				}
				map.put(KeyExplainHandler.ID_KEY,KeyExplainHandler.genKey(map, dc.getKeyClass()));
			}
		} catch (Exception e) {
			log.warn("BCheckStatus get error..", e);
		}
		if (para.isPage()) {
			return new ListInfo<>(totalCount, list, para);
		} else {
			return list;
		}
	}
	
	/**
	 * ajax根据ID主键查询
	 * 请求 url: 'http://ip/app/bcheckstatus/(_id值)' 
	 * dataType: 'json' 
	 * type: 'get'
	 */
	@RequestMapping(value="/{key:.*}",method=RequestMethod.GET)
	@ResponseBody
	public ListInfo<BCheckStatus> get(@PathVariable String key,HttpServletRequest req) {
		int totalCount = 1;
		List<BCheckStatus> list = null;
		try {
			BCheckStatus akey = new BCheckStatus();
				
				Field keyField=FieldUtils.allDeclaredField(BCheckStatusKey.class).get(0);

				if(keyField.getType().isInstance(1)){
					FieldUtils.setObjectValue(akey, keyField, Integer.parseInt(key));
				}else if(keyField.getType().isInstance(1L)){
					FieldUtils.setObjectValue(akey, keyField, Long.parseLong(key));
				}else{
					FieldUtils.setObjectValue(akey, keyField, key);
				}
				
//				Field keyField=FieldUtils.allDeclaredField(BCheckStatusKey.class).get(0);
//
//				if(keyField.getType().isInstance(1)){
//					FieldUtils.setObjectValue(akey, keyField, Integer.parseInt(key));
//				}else if(keyField.getType().isInstance(1L)){
//					FieldUtils.setObjectValue(akey, keyField, Long.parseLong(key));
//				}else{
//					FieldUtils.setObjectValue(akey, keyField, key);
//				}
//				
			
			if(true && akey.getTrandate() == null  && akey.getChecktype() == null ){
				list = new ArrayList<BCheckStatus>();
			}else{
				BCheckStatusExample example = new BCheckStatusExample();
				example.createCriteria().andTrandateEqualTo(akey.getTrandate()).andChecktypeEqualTo(akey.getChecktype());
				list = bCheckStatusMapper.selectByExample(example);
			}
			totalCount = list.size();
		} catch (Exception e) {
			log.warn("BCheckStatusCtrl get by key error..",e);
		}
		return  new ListInfo<>(totalCount, list, 0, 1);
	}
	
	/**
	 * ajax根据主键删除 
	 * url:'http://ip/app/bcheckstatus/(_id值)' 
	 * type: 'DELETE' 
	 * dataType: 'json' 
	 */
	@RequestMapping(value="/{key:.*}",method=RequestMethod.DELETE)
	@ResponseBody
	public ReturnInfo delete(@PathVariable String key,HttpServletRequest req) {
		try {
			BCheckStatus akey = new BCheckStatus();
				
				Field keyField=FieldUtils.allDeclaredField(BCheckStatusKey.class).get(0);

				if(keyField.getType().isInstance(1)){
					FieldUtils.setObjectValue(akey, keyField, Integer.parseInt(key));
				}else if(keyField.getType().isInstance(1L)){
					FieldUtils.setObjectValue(akey, keyField, Long.parseLong(key));
				}else{
					FieldUtils.setObjectValue(akey, keyField, key);
				}
				
//				Field keyField=FieldUtils.allDeclaredField(BCheckStatusKey.class).get(0);
//
//				if(keyField.getType().isInstance(1)){
//					FieldUtils.setObjectValue(akey, keyField, Integer.parseInt(key));
//				}else if(keyField.getType().isInstance(1L)){
//					FieldUtils.setObjectValue(akey, keyField, Long.parseLong(key));
//				}else{
//					FieldUtils.setObjectValue(akey, keyField, key);
//				}
				
			if(true && akey.getTrandate() != null  && akey.getChecktype() != null ){
				bCheckStatusMapper.deleteByPrimaryKey(akey);
				return ReturnInfo.Success;
			}
		} catch (Exception e) {
			log.warn("BCheckStatusCtrl delete by key error..");
		}
		return ReturnInfo.Faild;
	}
	
	/**
	 * ajax根据主键单条修改 
	 * url:'http://ip/app/bcheckstatus/(_id值)' 
	 * data:'{"key1":"value1","key2":"value2",...}' 
	 * type:'PUT'
	 */
	@RequestMapping(value="/{key:.*}",method=RequestMethod.PUT)
	@ResponseBody
	public ReturnInfo update(@PathVariable String key,@RequestBody BCheckStatus info,HttpServletRequest req) {
		try {
			if(info!=null){
				BCheckStatus akey = new BCheckStatus();
				
				Field keyField=FieldUtils.allDeclaredField(BCheckStatusKey.class).get(0);

				if(keyField.getType().isInstance(1)){
					FieldUtils.setObjectValue(akey, keyField, Integer.parseInt(key));
				}else if(keyField.getType().isInstance(1L)){
					FieldUtils.setObjectValue(akey, keyField, Long.parseLong(key));
				}else{
					FieldUtils.setObjectValue(akey, keyField, key);
				}
				
//				Field keyField=FieldUtils.allDeclaredField(BCheckStatusKey.class).get(0);
//
//				if(keyField.getType().isInstance(1)){
//					FieldUtils.setObjectValue(akey, keyField, Integer.parseInt(key));
//				}else if(keyField.getType().isInstance(1L)){
//					FieldUtils.setObjectValue(akey, keyField, Long.parseLong(key));
//				}else{
//					FieldUtils.setObjectValue(akey, keyField, key);
//				}
				BCheckStatusExample example = new BCheckStatusExample();
				example.createCriteria().andTrandateEqualTo(akey.getTrandate()).andChecktypeEqualTo(akey.getChecktype());
				bCheckStatusMapper.updateByExampleSelective(info, example);
			}
			return ReturnInfo.Success;
		} catch (Exception e) {
			log.warn("BCheckStatus update by key error..");
		}
		return ReturnInfo.Faild;
	}
	
	private void setTableName(DbCondi dc){
		String tName = DBBean.getTableName2Class(BCheckStatus.class);
		if(dc.getOther() == null){
			Map<String,Object> o = new HashMap<String,Object>();
			o.put(SqlMaker.TABLE_NAME, tName);
			dc.setOther(o);
		}else{
			dc.getOther().put(SqlMaker.TABLE_NAME, tName);
		}
	}
	
	@SuppressWarnings("serial")
	public static class BCheckStatuss extends ArrayList<BCheckStatus> {  
	    public BCheckStatuss() { super(); }  
	}
	
	/**
	 * ajax根据主键单条修改 url:'http://ip/app/BCheckStatus/(_id值)'
	 * data:'{"key1":"value1","key2":"value2",...}' type:'PUT'
	 */
	@RequestMapping(value = "/reChecking", method = RequestMethod.POST)
	@ResponseBody
	public ReturnInfo reChecking(HttpServletRequest req) {

		try {
			String jobdate = req.getParameter("jobdate");
			TCPSender sender = TCPSender.getInstance();
			String tranCode = "col003";

			log.info("get message success ! jobdate=[" + jobdate + "] !");

			String rescode = "";
			try {
				// 开始拼接报文
				RecheckingReq rechecking = new RecheckingReq();
				Head head = new Head();
				head.setTranCode(tranCode);
				String flowno = FlowNoGenerator.instance.getFlowNo();
				head.setFlowno(flowno);
				RecheckingReq.Body body = new RecheckingReq.Body();
				rechecking.setHead(head);
				rechecking.setBody(body);
				body.setjobdate(jobdate);
				// 发送请求，并将接收到的报文打印出来！
				byte[] reqMsg = sender.beanToXml(rechecking, RecheckingReq.class);
				log.info("req msg is [" + new String(reqMsg, ConsoleConstant.encoding) + "]");
				byte[] resMsg = sender.sendMsg(reqMsg);
				log.info("res msg is [" + new String(resMsg, ConsoleConstant.encoding) + "]");
				rechecking = (RecheckingReq) sender.xmlTobean(resMsg,RecheckingReq.class);

				rescode = rechecking.getHead().getRespcode();
				String resmsg = rechecking.getHead().getRespmsg();
				log.debug("response code is [" + rescode + "] response msg is [" + resmsg + "]");
			} catch (Exception e) {
				log.error("recheck error ! ", e);
			}

			// 判断是否正确返回
			if (rescode.equals("CBP000")) {
				return ReturnInfo.Success;
			} else {
				return ReturnInfo.Faild;
			}

		} catch (Exception e) {
			log.warn("BCheckStatus update by key error..", e);
		}
		return ReturnInfo.Faild;
	}
}