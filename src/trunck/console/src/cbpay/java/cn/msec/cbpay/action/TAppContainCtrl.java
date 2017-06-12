package cn.msec.cbpay.action;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.msec.cbpay.entity.TAppContain;
import cn.msec.cbpay.entity.TAppContainExample;
import cn.msec.cbpay.mapper.TAppContainMapper;
import cn.msec.cbpay.util.ConsoleConstant;
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

import com.ztkx.cbpay.invoker.InvokerConstant;
import com.ztkx.cbpay.invoker.bean.InvokerParams;
import com.ztkx.cbpay.invoker.client.ClientInvokerUtil;
import com.ztkx.cbpay.platformutil.baseconfig.BaseConfig;
import com.ztkx.cbpay.platformutil.baseconfig.ConstantConfigField;
import com.ztkx.cbpay.platformutil.log.FlowNoContainer;

@Controller
@RequestMapping("/tappcontain")
public class TAppContainCtrl extends BasicCtrl {

	private static TAppContainMapper tAppContainMapper = 
			(TAppContainMapper)BeanFactory.getBean("tAppContainMapper");
	
	private static DataService mysqlDataService = 
			(DataService)BeanFactory.getBean("mysqlDataService");
	Logger log = Logger.getLogger(TAppContainCtrl.class);
	/**
	 * ajax单条数据插入 
	 * url:'http://ip/rest/tappcontain' 
	 * data:'{"key1":"value1","key2":"value2",...}' 
	 * type:’POST
	 */
	@RequestMapping(value="",method=RequestMethod.POST)
	@ResponseBody
	public ReturnInfo insert( @RequestBody TAppContain info,HttpServletRequest req) {
		try {
			tAppContainMapper.insert(info);
			return ReturnInfo.Success;
		} catch (Exception e) {
			log.warn("TAppContainCtrl insert error..",e);
//			e.printStackTrace();
		}
		return ReturnInfo.Faild;
	}
	
	/**
	 * ajax精确查询请求 
	 * url: 'http://ip/app/tappcontain/?query=({"key1":"value1","key2":"value2",...})' 
	 * dataType: 'json' 
	 * type: 'get'
	 * 
	 * ajax无条件查询全部请�?	 * url: 'http://ip/app/tappcontain' 
	 * dataType: 'json' 
	 * type: 'get' 
	 * 
	 * ajax模糊查询请求 
	 * url: 'http://ip/app/tappcontain/?query={"(colname)":{"$regex":"(colvalue)","$options":"i"} 
	 * dataType: 'json' 
	 * type: 'get'
	 * 
	 * ajax分页查询
	 * 请求 url: 'http://ip/app/tappcontain/?query=(空或{"key1":"value1","key2":"value2",...})&skip=" + beginRow + "&limit=" + rowNum' 
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
			dc.setEntityClass(TAppContain.class);
			dc.setKeyClass(TAppContain.class);
			dc.setMapperClass(TAppContainMapper.class);
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
				map.put(KeyExplainHandler.ID_KEY, KeyExplainHandler.genKey(map, dc.getKeyClass()));
			}
		} catch (Exception e) {
			log.warn("TAppContainCtrl get error..",e);
		}
		if(para.isPage()){
			return new ListInfo<>(totalCount, list, para);
		}else{
			return list;
		}
	}
	
	
	@RequestMapping(value = "load", method = RequestMethod.GET)
	@ResponseBody
	private String loaddate(String invokerId){
		String sql = findsql(invokerId);
		String result = "";
		try {
			//1.查询需要发送的容器
			List<HashMap> list = SerializerUtil.deserializeArray(mysqlDataService.doBySQL(sql), HashMap.class);
			String rondom = ConsoleConstant.getRondom();
			FlowNoContainer.setFlowNo(rondom);
			if(null!=list){
				log.info("contain size : "+list.size());
				
				InvokerParams para = new InvokerParams();
				para.setInvokerId(invokerId);
				para.setOperator(InvokerConstant.OPR_PRELOAD);	//预加载数据
				para.setSourceNode(BaseConfig.getConfig(ConstantConfigField.BASECONF_CONTAINER_NAME));
				para.setMode(InvokerParams.MODE_SYN);
				para.setAutoCommit(true);		//当前命令自动提交
				para.setSerialId(rondom);
				List<String> targetNodes = new ArrayList<String>();
				for(HashMap map : list){
					targetNodes.add((String)map.get("APPID")+"_"+(String)map.get("CONTAINTYPE"));
				}
				para.setTargetNodes(targetNodes);
				
				int OverTime = Integer.parseInt(BaseConfig.getConfig(ConstantConfigField.OVER_TIME));
				
				//预加载数据
				Map<String, InvokerParams> invokerMap = ClientInvokerUtil.sendInvokParam(para);
				boolean isSucc =  ClientInvokerUtil.isSucc(invokerMap, OverTime);
				if(isSucc){
					//加载平台数据
					para.setOperator(InvokerConstant.OPR_RELOAD);
					invokerMap = ClientInvokerUtil.sendInvokParam(para);
					isSucc =  ClientInvokerUtil.isSucc(invokerMap, OverTime);
					
				}else{
					para.setOperator(InvokerConstant.OPR_ROLLBAK);
					invokerMap = ClientInvokerUtil.sendInvokParam(para);
					isSucc =  ClientInvokerUtil.isSucc(invokerMap, OverTime);
				}
				result = getRes(list, invokerMap);
			}
		} catch (Exception e) {
			log.error("send to loaddate error", e);
			result = "systemerror";
		}
		if(log.isDebugEnabled()){
			log.debug("return result["+result+"]");
		}
		return result;
	}
	
	private String getRes(List<HashMap> list,Map<String, InvokerParams> invokerMap){
		
		StringBuilder sb = new StringBuilder();
		for(HashMap map : list){
			String appid = (String)map.get("APPID");
			String containtype = (String)map.get("CONTAINTYPE");
			String ip = (String)map.get("IP");
			InvokerParams param = invokerMap.get(appid+"_"+containtype);
			sb.append(appid+":"+containtype+":"+ip+":"+param.getOperator()+":"+param.isSucc()+"-");
		}
		return sb.toString();
	}
	
	private String findsql(String command){
		String sql = "select * from t_app_contain where valid='1' order by appid,containtype";
		
		/*switch (command) {
		case "loaddate":
			sql = "select * from t_app_contain where containtype in ('IN','OUT')";
			break;
		case "channelinfo":
			sql = "select * from t_app_contain where containtype in ('IN','OUT')";
			break;
		default:
			log.error("command["+command+"] is not match");
			break;
		}*/
		return sql;
	}
	/**
	 * ajax根据ID主键查询
	 * 请求 url: 'http://ip/app/tappcontain/(_id�?' 
	 * dataType: 'json' 
	 * type: 'get'
	 */
	@RequestMapping(value="/{key:.*}",method=RequestMethod.GET)
	@ResponseBody
	public ListInfo<TAppContain> get(@PathVariable String key,HttpServletRequest req) {
		int totalCount = 1;
		List<TAppContain> list = null;
		try {
			TAppContain akey = new TAppContain();
				
			
			if(true){
				list = new ArrayList<TAppContain>();
			}else{
				TAppContainExample example = new TAppContainExample();
				example.createCriteria();
				list = tAppContainMapper.selectByExample(example);
			}
			totalCount = list.size();
		} catch (Exception e) {
			log.warn("TAppContainCtrl get by key error..",e);
		}
		return  new ListInfo<>(totalCount, list, 0, 1);
	}
	
	/**
	 * ajax根据主键删除 
	 * url:'http://ip/app/tappcontain/(_id�?' 
	 * type: 'DELETE' 
	 * dataType: 'json' 
	 */
	@RequestMapping(value="/{key:.*}",method=RequestMethod.DELETE)
	@ResponseBody
	public ReturnInfo delete(@PathVariable String key,HttpServletRequest req) {
		try {
			TAppContain akey = new TAppContain();
				
			if(true){
				tAppContainMapper.deleteByPrimaryKey(akey);
				return ReturnInfo.Success;
			}
		} catch (Exception e) {
			log.warn("TAppContainCtrl delete by key error..");
		}
		return ReturnInfo.Faild;
	}
	
	/**
	 * ajax根据主键单条修改 
	 * url:'http://ip/app/tappcontain/(_id�?' 
	 * data:'{"key1":"value1","key2":"value2",...}' 
	 * type:'PUT'
	 */
	@RequestMapping(value="/{key:.*}",method=RequestMethod.PUT)
	@ResponseBody
	public ReturnInfo update(@PathVariable String key,@RequestBody TAppContain info,HttpServletRequest req) {
		try {
			if(info!=null){
				TAppContain akey = new TAppContain();
				TAppContainExample example = new TAppContainExample();
				example.createCriteria();
				tAppContainMapper.updateByExampleSelective(info, example);
			}
			return ReturnInfo.Success;
		} catch (Exception e) {
			log.warn("TAppContain update by key error..");
		}
		return ReturnInfo.Faild;
	}
	
	private void setTableName(DbCondi dc){
		String tName = DBBean.getTableName2Class(TAppContain.class);
		if(dc.getOther() == null){
			Map<String,Object> o = new HashMap<String,Object>();
			o.put(SqlMaker.TABLE_NAME, tName);
			dc.setOther(o);
		}else{
			dc.getOther().put(SqlMaker.TABLE_NAME, tName);
		}
	}
	
	@SuppressWarnings("serial")
	public static class TAppContains extends ArrayList<TAppContain> {  
	    public TAppContains() { super(); }  
	}
}