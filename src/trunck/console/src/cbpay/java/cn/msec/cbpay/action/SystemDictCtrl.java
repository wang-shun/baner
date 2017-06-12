package cn.msec.cbpay.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.msec.cbpay.entity.SystemDict;
import cn.msec.cbpay.entity.SystemDictExample;
import cn.msec.cbpay.mapper.SystemDictMapper;
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
import cn.msec.rest.web.bind.RequestJsonParam;

@Slf4j
@Controller
@RequestMapping("/systemdict")
public class SystemDictCtrl extends BasicCtrl {

	private static SystemDictMapper systemDictMapper = 
			(SystemDictMapper)BeanFactory.getBean("systemDictMapper");
	
	private static DataService mysqlDataService = 
			(DataService)BeanFactory.getBean("mysqlDataService");
	
	/**
	 * ajax单条数据插入 
	 * url:'http://ip/rest/systemdict' 
	 * data:'{"key1":"value1","key2":"value2",...}' 
	 * type:’POST
	 */
	@RequestMapping(value="",method=RequestMethod.POST)
	@ResponseBody
	public ReturnInfo insert( @RequestBody SystemDict info,HttpServletRequest req) {
		try {
			systemDictMapper.insert(info);
			return ReturnInfo.Success;
		} catch (Exception e) {
			log.warn("SystemDictCtrl insert error..",e);
//			e.printStackTrace();
		}
		return ReturnInfo.Faild;
	}
	
	/**
	 * ajax精确查询请求 
	 * url: 'http://ip/app/systemdict/?query=({"key1":"value1","key2":"value2",...})' 
	 * dataType: 'json' 
	 * type: 'get'
	 * 
	 * ajax无条件查询全部请�?	 * url: 'http://ip/app/systemdict' 
	 * dataType: 'json' 
	 * type: 'get' 
	 * 
	 * ajax模糊查询请求 
	 * url: 'http://ip/app/systemdict/?query={"(colname)":{"$regex":"(colvalue)","$options":"i"} 
	 * dataType: 'json' 
	 * type: 'get'
	 * 
	 * ajax分页查询
	 * 请求 url: 'http://ip/app/systemdict/?query=(空或{"key1":"value1","key2":"value2",...})&skip=" + beginRow + "&limit=" + rowNum' 
	 * dataType:'json' 
	 * type:'get' 
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/currency", method = RequestMethod.GET)
	@ResponseBody
	public Object getCurrency(@RequestJsonParam(value = "query",required=false) QueryMapperBean info,
			@RequestJsonParam(value = "fields",required=false) FieldsMapperBean fmb,
			PageInfo para, HttpServletRequest req) {
		int totalCount = 0;
		List<HashMap> list = null;
		try {
			DbCondi dc = new DbCondi();
			dc.setEntityClass(SystemDict.class);
			dc.setMapperClass(SystemDictMapper.class);
			String json = "{\"type\":\"1\"}";
			ObjectMapper mapper = new ObjectMapper(); 
			JsonNode rootNode = mapper.readTree(json);
			if(info==null){
				info = new QueryMapperBean();
			}
			info.setNode(rootNode);
			dc.setQmb(info);
			dc.setPageinfo(para);
			dc.setFmb(fmb);
			this.setTableName(dc);
			String sql = SqlMaker.getReferenceCountSql(dc);
			totalCount = mysqlDataService.getCount(sql);
			sql = SqlMaker.getReferenceData(dc); 
			list = SerializerUtil.deserializeArray(mysqlDataService.doBySQL(sql), HashMap.class);
			
		} catch (Exception e) {
			log.warn("SystemDictCtrl get error..",e);
		}
		if(para.isPage()){
			return new ListInfo<>(totalCount, list, para);
		}else{
			return list;
		}
	}
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/bank", method = RequestMethod.GET)
	@ResponseBody
	public Object getBank(@RequestJsonParam(value = "query",required=false) QueryMapperBean info,
			@RequestJsonParam(value = "fields",required=false) FieldsMapperBean fmb,
			PageInfo para, HttpServletRequest req) {
		int totalCount = 0;
		List<HashMap> list = null;
		List listValue = null;
		try {
			DbCondi dc = new DbCondi();
			dc.setEntityClass(SystemDict.class);
			dc.setMapperClass(SystemDictMapper.class);
			String json = "{\"type\":\"2\"}";
			ObjectMapper mapper = new ObjectMapper(); 
			JsonNode rootNode = mapper.readTree(json);
			if(info==null){
				info = new QueryMapperBean();
			}
			info.setNode(rootNode);
			dc.setQmb(info);
			dc.setPageinfo(para);
			dc.setFmb(fmb);
			this.setTableName(dc);
			String sql = SqlMaker.getReferenceCountSql(dc);
			totalCount = mysqlDataService.getCount(sql);
			sql = SqlMaker.getReferenceData(dc); 
			list = SerializerUtil.deserializeArray(mysqlDataService.doBySQL(sql), HashMap.class);
			listValue = new ArrayList(list.size()*3);
			for(HashMap map : list){
				listValue.add(map);
				String value = (String) map.get("key");
				HashMap mapsvr = new HashMap(map);
				mapsvr.put("key", value+"_SVR");
				listValue.add(mapsvr);
				HashMap mapcli = new HashMap(map);
				mapcli.put("key", value+"_CLI");
				listValue.add(mapcli);
			}
			
		} catch (Exception e) {
			log.warn("SystemDictCtrl get error..",e);
		}
		if(para.isPage()){
			return new ListInfo<>(totalCount, list, para);
		}else{
			return listValue;
		}
	}
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/trancode", method = RequestMethod.GET)
	@ResponseBody
	public Object getTrancode(@RequestJsonParam(value = "query",required=false) QueryMapperBean info,
			@RequestJsonParam(value = "fields",required=false) FieldsMapperBean fmb,
			PageInfo para, HttpServletRequest req) {
		int totalCount = 0;
		List<HashMap> list = null;
		List listValue = null;
		try {
			DbCondi dc = new DbCondi();
			dc.setEntityClass(SystemDict.class);
			dc.setMapperClass(SystemDictMapper.class);
			String json = "{\"type\":\"3\"}";
			ObjectMapper mapper = new ObjectMapper(); 
			JsonNode rootNode = mapper.readTree(json);
			if(info==null){
				info = new QueryMapperBean();
			}
			info.setNode(rootNode);
			dc.setQmb(info);
			dc.setPageinfo(para);
			dc.setFmb(fmb);
			this.setTableName(dc);
			String sql = SqlMaker.getReferenceCountSql(dc);
			totalCount = mysqlDataService.getCount(sql);
			sql = SqlMaker.getReferenceData(dc); 
			list = SerializerUtil.deserializeArray(mysqlDataService.doBySQL(sql), HashMap.class);
		} catch (Exception e) {
			log.warn("SystemDictCtrl get error..",e);
		}
		if(para.isPage()){
			return new ListInfo<>(totalCount, list, para);
		}else{
			return list;
		}
	}
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
			dc.setEntityClass(SystemDict.class);
			dc.setMapperClass(SystemDictMapper.class);
			dc.setQmb(info);
			dc.setPageinfo(para);
			dc.setFmb(fmb);
			this.setTableName(dc);
			String sql = SqlMaker.getReferenceCountSql(dc);
			totalCount = mysqlDataService.getCount(sql);
			sql = SqlMaker.getReferenceData(dc); 
			list = SerializerUtil.deserializeArray(mysqlDataService.doBySQL(sql), HashMap.class);
			
		} catch (Exception e) {
			log.warn("SystemDictCtrl get error..",e);
		}
		if(para.isPage()){
			return new ListInfo<>(totalCount, list, para);
		}else{
			return list;
		}
	}
	/**
	 * ajax根据ID主键查询
	 * 请求 url: 'http://ip/app/systemdict/(_id�?' 
	 * dataType: 'json' 
	 * type: 'get'
	 */
	@RequestMapping(value="/{key:.*}",method=RequestMethod.GET)
	@ResponseBody
	public ListInfo<SystemDict> get(@PathVariable String key,HttpServletRequest req) {
		int totalCount = 1;
		List<SystemDict> list = null;
		try {
			SystemDict akey = new SystemDict();
				
			
			if(true){
				list = new ArrayList<SystemDict>();
			}else{
				SystemDictExample example = new SystemDictExample();
				example.createCriteria();
				list = systemDictMapper.selectByExample(example);
			}
			totalCount = list.size();
		} catch (Exception e) {
			log.warn("SystemDictCtrl get by key error..",e);
		}
		return  new ListInfo<>(totalCount, list, 0, 1);
	}
	
	/**
	 * ajax根据主键删除 
	 * url:'http://ip/app/systemdict/(_id�?' 
	 * type: 'DELETE' 
	 * dataType: 'json' 
	 */
	@RequestMapping(value="/{key:.*}",method=RequestMethod.DELETE)
	@ResponseBody
	public ReturnInfo delete(@PathVariable String key,HttpServletRequest req) {
		try {
			SystemDict akey = new SystemDict();
				
			if(true){
				systemDictMapper.deleteByPrimaryKey(akey);
				return ReturnInfo.Success;
			}
		} catch (Exception e) {
			log.warn("SystemDictCtrl delete by key error..");
		}
		return ReturnInfo.Faild;
	}
	
	/**
	 * ajax根据主键单条修改 
	 * url:'http://ip/app/systemdict/(_id�?' 
	 * data:'{"key1":"value1","key2":"value2",...}' 
	 * type:'PUT'
	 */
	@RequestMapping(value="/{key:.*}",method=RequestMethod.PUT)
	@ResponseBody
	public ReturnInfo update(@PathVariable String key,@RequestBody SystemDict info,HttpServletRequest req) {
		try {
			if(info!=null){
				SystemDict akey = new SystemDict();
				SystemDictExample example = new SystemDictExample();
				example.createCriteria();
				systemDictMapper.updateByExampleSelective(info, example);
			}
			return ReturnInfo.Success;
		} catch (Exception e) {
			log.warn("SystemDict update by key error..");
		}
		return ReturnInfo.Faild;
	}
	
	private void setTableName(DbCondi dc){
		String tName = DBBean.getTableName2Class(SystemDict.class);
		if(dc.getOther() == null){
			Map<String,Object> o = new HashMap<String,Object>();
			o.put(SqlMaker.TABLE_NAME, tName);
			dc.setOther(o);
		}else{
			dc.getOther().put(SqlMaker.TABLE_NAME, tName);
		}
	}
	
	@SuppressWarnings("serial")
	public static class SystemDicts extends ArrayList<SystemDict> {  
	    public SystemDicts() { super(); }  
	}
}