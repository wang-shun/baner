package cn.msec.cbpay.action;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.msec.cbpay.entity.AppAddress;
import cn.msec.cbpay.entity.AppAddressExample;
import cn.msec.cbpay.mapper.AppAddressMapper;
import cn.msec.cbpay.util.ConsoleConstant;
import cn.msec.rest.db.service.DataService;
import cn.msec.rest.utils.BeanFactory;
import cn.msec.rest.utils.DBBean;
import cn.msec.rest.web.action.BasicCtrl;
import cn.msec.rest.web.bean.DbCondi;
import cn.msec.rest.web.bean.FieldsMapperBean;
import cn.msec.rest.web.bean.ListInfo;
import cn.msec.rest.web.bean.PageInfo;
import cn.msec.rest.web.bean.QueryMapperBean;
import cn.msec.rest.web.bean.ReturnInfo;
import cn.msec.rest.web.bean.SqlMaker;
import cn.msec.rest.web.bind.FieldUtils;
import cn.msec.rest.web.bind.RequestJsonParam;

import com.ztkx.cbpay.invoker.InvokerConstant;
import com.ztkx.cbpay.invoker.bean.InvokerParams;
import com.ztkx.cbpay.invoker.client.ClientInvokerUtil;
import com.ztkx.cbpay.platformutil.baseconfig.BaseConfig;
import com.ztkx.cbpay.platformutil.baseconfig.ConstantConfigField;
import com.ztkx.cbpay.platformutil.log.FlowNoContainer;

@Controller
@RequestMapping("/appaddress")
public class AppAddressCtrl extends BasicCtrl {

	private static AppAddressMapper appAddressMapper = 
			(AppAddressMapper)BeanFactory.getBean("appAddressMapper");
	
	private static DataService mysqlDataService = 
			(DataService)BeanFactory.getBean("mysqlDataService");
	

	/**
	 * ajax单条数据插入 
	 * url:'http://ip/rest/appaddress' 
	 * data:'{"key1":"value1","key2":"value2",...}' 
	 * type:’POST
	 */
	Logger log = Logger.getLogger(AppAddressCtrl.class);
	@RequestMapping(value="",method=RequestMethod.POST)
	public ReturnInfo insert( @RequestBody AppAddress info,HttpServletRequest req) {
		try {
			appAddressMapper.insert(info);
			return ReturnInfo.Success;
		} catch (Exception e) {
			log.warn("AppAddressCtrl insert error..",e);
//			e.printStackTrace();
		}
		return ReturnInfo.Faild;
	}
	/**
	 * 1.首先改变表数据
	 * 2.发送命令到discenter
	 * 3.线程等待直至被监听唤醒
	 * 4.唤醒后从消息队列取出消息
	 * 5.将结果返回给前台
	 * @param req
	 * @param rep
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/operate")
	public String operate(HttpServletRequest req,HttpServletResponse rep) {
		log.info("operate is begin");
		String rondom = ConsoleConstant.getRondom();
		FlowNoContainer.setFlowNo(rondom);
		String appid = req.getParameter(InvokerConstant.appid);
		String appstatus = req.getParameter(InvokerConstant.appstatus);
		int i = this.updateApp(appid, appstatus);
		String result = null;
		InvokerParams para = new InvokerParams();
		para.setInvokerId("appaddress");
		para.setOperator(InvokerConstant.OPR_PRELOAD);	//预加载数据
		para.setSourceNode(BaseConfig.getConfig(ConstantConfigField.BASECONF_CONTAINER_NAME));
		para.setMode(InvokerParams.MODE_SYN);
		para.setAutoCommit(true);		//当前命令自动提交
		para.setSerialId(rondom);
		Map<String,String> commandparam = new HashMap<String,String>();
		commandparam.put(InvokerConstant.appid, appid);
		commandparam.put(InvokerConstant.appstatus, appstatus);
		para.setCommandparam(commandparam);
		
		List<String> targetNodes = new ArrayList<String>();
		targetNodes.add(ConstantConfigField.CONTAINER_NAME_DISCENTER);
		para.setTargetNodes(targetNodes);
		
		int OverTime = Integer.parseInt(BaseConfig.getConfig(ConstantConfigField.OVER_TIME));
		log.info("OverTime["+OverTime+"]");
		if("0".equals(appstatus)){
			appstatus = "1";
		}else{
			appstatus = "0";
		}
		try{
			Map<String, InvokerParams> invokerMap = ClientInvokerUtil.sendInvokParam(para);
			boolean isSucc =  ClientInvokerUtil.isSucc(invokerMap, OverTime);
			if(isSucc){
				log.info("operate is succ");
				result = "succ";
			}else{
				log.error("operate is fail");
				this.updateApp(appid, appstatus);
				result = "fail";
			}
		}catch(Exception e){
			this.updateApp(appid, appstatus);
			log.error("appaddress is error",e);
			result = "fail";
		}
		log.info("operate is end");
		return result;
	}
	
	/**
	 * 更改数据库表
	 * @param appid
	 * @param appstatus
	 * @return
	 */
	private int updateApp(String appid,String appstatus){
		AppAddress appAddress = new AppAddress();
		appAddress.setAppid(appid);
		appAddress.setAppstatus(appstatus);
		return appAddressMapper.updateByPrimaryKey(appAddress);
	}
	
	
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "", method = RequestMethod.GET)
	@ResponseBody
	public Object get(@RequestJsonParam(value = "query",required=false) QueryMapperBean info,
			@RequestJsonParam(value = "fields",required=false) FieldsMapperBean fmb,
			PageInfo param, HttpServletRequest req) {
		log.info("get appaddress is begin");
		String rondom = ConsoleConstant.getRondom();
		FlowNoContainer.setFlowNo(rondom);
		List<HashMap> list = null;
		
		InvokerParams para = new InvokerParams();
		para.setInvokerId("queryappaddress");
		para.setOperator(InvokerConstant.OPR_PRELOAD);	//预加载数据
		para.setSourceNode(BaseConfig.getConfig(ConstantConfigField.BASECONF_CONTAINER_NAME));
		para.setMode(InvokerParams.MODE_SYN);
		para.setAutoCommit(true);		//当前命令自动提交
		para.setSerialId(rondom);
		
		Map<String,String> commandparam = new HashMap<String,String>();
		para.setCommandparam(commandparam);
		
		List<String> targetNodes = new ArrayList<String>();
		targetNodes.add(ConstantConfigField.CONTAINER_NAME_DISCENTER);
		para.setTargetNodes(targetNodes);
		
		int OverTime = Integer.parseInt(BaseConfig.getConfig(ConstantConfigField.OVER_TIME));
		log.info("OverTime["+OverTime+"]");
		try{
			Map<String, InvokerParams> invokerMap = ClientInvokerUtil.sendInvokParam(para);
			boolean isSucc =  ClientInvokerUtil.isSucc(invokerMap, OverTime);
			if(isSucc){
				InvokerParams params = invokerMap.get(ConstantConfigField.CONTAINER_NAME_DISCENTER);
				String result = params.getCommandparam().get(InvokerConstant.result);
				log.info("operate is succ result["+result+"]");
				list = parseResult(result);
				
			}else{
				log.error("operate is fail");
			}
		}catch(Exception e){
			log.error("appaddress is error",e);
		}
		log.info("get appaddress is end");
		return new ListInfo<>(list.size(), list, 0, 1);
	}
	private List<HashMap> parseResult(String result){
		List<HashMap> resultList = new ArrayList<HashMap>();
		String[] resultlistArray = result.split(InvokerConstant.SEPARATE_ONE);
		if(resultlistArray!=null){
			for(String resultListtmp:resultlistArray){
				HashMap<String,String> resultMap = new HashMap<String,String>();
				String[] resultMapArray = resultListtmp.split(InvokerConstant.SEPARATE_TWO);
				for(String resultMaptmp : resultMapArray){
					String[] resultArray = resultMaptmp.split(InvokerConstant.SEPARATE_THREE);
					if(resultArray[1].contains("<PROVIDER_URL>")){
						resultArray[1] = resultArray[1].substring(resultArray[1].indexOf("<PROVIDER_URL>")+"<PROVIDER_URL>".length(), resultArray[1].indexOf("</PROVIDER_URL>"));
					}
					resultMap.put(resultArray[0], resultArray[1]);
				}
				resultList.add(resultMap);
			}
		}
		return resultList;
	}
	/**
	 * ajax根据ID主键查询
	 * 请求 url: 'http://ip/app/appaddress/(_id�?' 
	 * dataType: 'json' 
	 * type: 'get'
	 */
	@RequestMapping(value="/{key:.*}",method=RequestMethod.GET)
	@ResponseBody
	public ListInfo<AppAddress> get(@PathVariable String key,HttpServletRequest req) {
		int totalCount = 1;
		List<AppAddress> list = null;
		try {
			AppAddress akey = new AppAddress();
				
				Field keyField=FieldUtils.allDeclaredField(AppAddress.class).get(0);

				if(keyField.getType().isInstance(1)){
					FieldUtils.setObjectValue(akey, keyField, Integer.parseInt(key));
				}else if(keyField.getType().isInstance(1L)){
					FieldUtils.setObjectValue(akey, keyField, Long.parseLong(key));
				}else{
					FieldUtils.setObjectValue(akey, keyField, key);
				}
				
			
			if(true && akey.getAppid() == null  && akey.getType() == null ){
				list = new ArrayList<AppAddress>();
			}else{
				AppAddressExample example = new AppAddressExample();
				example.createCriteria().andAppidEqualTo(akey.getAppid()).andTypeEqualTo(akey.getType());
				list = appAddressMapper.selectByExample(example);
			}
			totalCount = list.size();
		} catch (Exception e) {
			log.warn("AppAddressCtrl get by key error..",e);
		}
		return  new ListInfo<>(totalCount, list, 0, 1);
	}
	
	/**
	 * ajax根据主键删除 
	 * url:'http://ip/app/appaddress/(_id�?' 
	 * type: 'DELETE' 
	 * dataType: 'json' 
	 */
	@RequestMapping(value="/{key:.*}",method=RequestMethod.DELETE)
	@ResponseBody
	public ReturnInfo delete(@PathVariable String key,HttpServletRequest req) {
		try {
			AppAddress akey = new AppAddress();
				
				Field keyField=FieldUtils.allDeclaredField(AppAddress.class).get(0);

				if(keyField.getType().isInstance(1)){
					FieldUtils.setObjectValue(akey, keyField, Integer.parseInt(key));
				}else if(keyField.getType().isInstance(1L)){
					FieldUtils.setObjectValue(akey, keyField, Long.parseLong(key));
				}else{
					FieldUtils.setObjectValue(akey, keyField, key);
				}
				
				
			if(true && akey.getAppid() != null  && akey.getType() != null ){
				appAddressMapper.deleteByPrimaryKey(akey);
				return ReturnInfo.Success;
			}
		} catch (Exception e) {
			log.warn("AppAddressCtrl delete by key error..");
		}
		return ReturnInfo.Faild;
	}
	
	/**
	 * ajax根据主键单条修改 
	 * url:'http://ip/app/appaddress/(_id�?' 
	 * data:'{"key1":"value1","key2":"value2",...}' 
	 * type:'PUT'
	 */
	@RequestMapping(value="/{key:.*}",method=RequestMethod.PUT)
	@ResponseBody
	public ReturnInfo update(@PathVariable String key,@RequestBody AppAddress info,HttpServletRequest req) {
		try {
			if(info!=null){
				AppAddress akey = new AppAddress();
				
				Field keyField=FieldUtils.allDeclaredField(AppAddress.class).get(0);

				if(keyField.getType().isInstance(1)){
					FieldUtils.setObjectValue(akey, keyField, Integer.parseInt(key));
				}else if(keyField.getType().isInstance(1L)){
					FieldUtils.setObjectValue(akey, keyField, Long.parseLong(key));
				}else{
					FieldUtils.setObjectValue(akey, keyField, key);
				}
			}
			return ReturnInfo.Success;
		} catch (Exception e) {
			log.warn("AppAddress update by key error..");
		}
		return ReturnInfo.Faild;
	}
	
	private void setTableName(DbCondi dc){
		String tName = DBBean.getTableName2Class(AppAddress.class);
		if(dc.getOther() == null){
			Map<String,Object> o = new HashMap<String,Object>();
			o.put(SqlMaker.TABLE_NAME, tName);
			dc.setOther(o);
		}else{
			dc.getOther().put(SqlMaker.TABLE_NAME, tName);
		}
	}
	
	@SuppressWarnings("serial")
	public static class AppAddresss extends ArrayList<AppAddress> {  
	    public AppAddresss() { super(); }  
	}
}