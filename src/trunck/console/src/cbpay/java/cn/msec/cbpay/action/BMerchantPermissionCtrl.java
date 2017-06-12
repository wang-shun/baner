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

import cn.msec.cbpay.entity.BMerchantInfo;
import cn.msec.cbpay.entity.BMerchantPermission;
import cn.msec.cbpay.entity.BMerchantPermissionExample;
import cn.msec.cbpay.mapper.BMerchantInfoMapper;
import cn.msec.cbpay.mapper.BMerchantPermissionMapper;
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
@RequestMapping("/bmerchantpermission")
public class BMerchantPermissionCtrl extends BasicCtrl {

	private static BMerchantPermissionMapper bMerchantPermissionMapper = 
			(BMerchantPermissionMapper)BeanFactory.getBean("bMerchantPermissionMapper");
	
	//private static Map<String,String> mapinfo = new HashMap<String,String>();
	
	private static BMerchantInfoMapper bMerchantInfoMapper = (BMerchantInfoMapper) BeanFactory
			.getBean("bMerchantInfoMapper");
	
	private static DataService mysqlDataService = 
			(DataService)BeanFactory.getBean("mysqlDataService");
	
	/**
	 * ajax单条数据插入 
	 * url:'http://ip/rest/bmerchantpermission' 
	 * data:'{"key1":"value1","key2":"value2",...}' 
	 * type:’POST
	 */
	@RequestMapping(value="",method=RequestMethod.POST)
	@ResponseBody
	public ReturnInfo insert( @RequestBody BMerchantPermission info,HttpServletRequest req) {
		try {
			bMerchantPermissionMapper.insert(info);
			return ReturnInfo.Success;
		} catch (Exception e) {
			log.warn("BMerchantPermissionCtrl insert error..",e);
//			e.printStackTrace();
		}
		return ReturnInfo.Faild;
	}
	
	/**
	 * ajax精确查询请求 
	 * url: 'http://ip/app/bmerchantpermission/?query=({"key1":"value1","key2":"value2",...})' 
	 * dataType: 'json' 
	 * type: 'get'
	 * 
	 * ajax无条件查询全部请�?	 * url: 'http://ip/app/bmerchantpermission' 
	 * dataType: 'json' 
	 * type: 'get' 
	 * 
	 * ajax模糊查询请求 
	 * url: 'http://ip/app/bmerchantpermission/?query={"(colname)":{"$regex":"(colvalue)","$options":"i"} 
	 * dataType: 'json' 
	 * type: 'get'
	 * 
	 * ajax分页查询
	 * 请求 url: 'http://ip/app/bmerchantpermission/?query=(空或{"key1":"value1","key2":"value2",...})&skip=" + beginRow + "&limit=" + rowNum' 
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
			dc.setEntityClass(BMerchantPermission.class);
			dc.setKeyClass(BMerchantPermission.class);
			dc.setMapperClass(BMerchantPermissionMapper.class);
			dc.setQmb(info);
			dc.setPageinfo(para);
			dc.setFmb(fmb);
			this.setTableName(dc);
			String sql = SqlMaker.getReferenceCountSql(dc);
			totalCount = mysqlDataService.getCount(sql);
			sql = SqlMaker.getReferenceData(dc); 
			list = SerializerUtil.deserializeArray(mysqlDataService.doBySQL(sql), HashMap.class);
			List<BMerchantInfo> listinfo = bMerchantInfoMapper.selectAll();
			Map<String,String> mapinfo = new HashMap<String,String>();
			for(BMerchantInfo merchantinfo : listinfo){
				mapinfo.put(merchantinfo.getMerchantid(), merchantinfo.getMerchantname());
			}
			for(HashMap map : list){
				for(Field filed:FieldUtils.allDeclaredField(dc.getKeyClass())){
					if(map.get(filed.getName())==null){
						map.put(filed.getName(), "");
					}
				}
				map.put("merchantname", mapinfo.get(map.get("merchantid")));
				map.put(KeyExplainHandler.ID_KEY, KeyExplainHandler.genKey(map, dc.getKeyClass()));
			}
		} catch (Exception e) {
			log.warn("BMerchantPermissionCtrl get error..",e);
		}
		if(para.isPage()){
			return new ListInfo<>(totalCount, list, para);
		}else{
			return list;
		}
	}
	
	/**
	 * ajax根据ID主键查询
	 * 请求 url: 'http://ip/app/bmerchantpermission/(_id�?' 
	 * dataType: 'json' 
	 * type: 'get'
	 */
	@RequestMapping(value="/{key:.*}",method=RequestMethod.GET)
	@ResponseBody
	public ListInfo<BMerchantPermission> get(@PathVariable String key,HttpServletRequest req) {
		int totalCount = 1;
		List<BMerchantPermission> list = null;
		try {
			BMerchantPermission akey = new BMerchantPermission();
				
			
			if(true){
				list = new ArrayList<BMerchantPermission>();
			}else{
				BMerchantPermissionExample example = new BMerchantPermissionExample();
				example.createCriteria();
				list = bMerchantPermissionMapper.selectByExample(example);
			}
			totalCount = list.size();
		} catch (Exception e) {
			log.warn("BMerchantPermissionCtrl get by key error..",e);
		}
		return  new ListInfo<>(totalCount, list, 0, 1);
	}
	
	/**
	 * ajax根据主键删除 
	 * url:'http://ip/app/bmerchantpermission/(_id�?' 
	 * type: 'DELETE' 
	 * dataType: 'json' 
	 */
	@RequestMapping(value="/{key:.*}",method=RequestMethod.DELETE)
	@ResponseBody
	public ReturnInfo delete(@PathVariable String key,HttpServletRequest req) {
		try {
			BMerchantPermission akey = new BMerchantPermission();
				
			if(true){
				bMerchantPermissionMapper.deleteByPrimaryKey(akey);
				return ReturnInfo.Success;
			}
		} catch (Exception e) {
			log.warn("BMerchantPermissionCtrl delete by key error..");
		}
		return ReturnInfo.Faild;
	}
	
	/**
	 * ajax根据主键单条修改 
	 * url:'http://ip/app/bmerchantpermission/(_id�?' 
	 * data:'{"key1":"value1","key2":"value2",...}' 
	 * type:'PUT'
	 */
	@RequestMapping(value="/{key:.*}",method=RequestMethod.PUT)
	@ResponseBody
	public ReturnInfo update(@PathVariable String key,@RequestBody BMerchantPermission info,HttpServletRequest req) {
		try {
			if(info!=null){
				BMerchantPermission akey = new BMerchantPermission();
				BMerchantPermissionExample example = new BMerchantPermissionExample();
				example.createCriteria();
				bMerchantPermissionMapper.updateByExampleSelective(info, example);
			}
			return ReturnInfo.Success;
		} catch (Exception e) {
			log.warn("BMerchantPermission update by key error..");
		}
		return ReturnInfo.Faild;
	}
	
	private void setTableName(DbCondi dc){
		String tName = DBBean.getTableName2Class(BMerchantPermission.class);
		if(dc.getOther() == null){
			Map<String,Object> o = new HashMap<String,Object>();
			o.put(SqlMaker.TABLE_NAME, tName);
			dc.setOther(o);
		}else{
			dc.getOther().put(SqlMaker.TABLE_NAME, tName);
		}
	}
	
	@SuppressWarnings("serial")
	public static class BMerchantPermissions extends ArrayList<BMerchantPermission> {  
	    public BMerchantPermissions() { super(); }  
	}
}