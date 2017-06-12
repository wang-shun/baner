package cn.msec.msc.mysqlgens.web.action;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.msec.msc.mysqlgens.admin.entity.SysMenu;
import cn.msec.msc.mysqlgens.admin.entity.SysMenuExample;
import cn.msec.msc.mysqlgens.admin.entity.SysMenuKey;
import cn.msec.msc.mysqlgens.admin.mapper.SysMenuMapper;
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
@RequestMapping("/sysmenu")
public class SysMenuCtrl extends BasicCtrl {

	private static SysMenuMapper sysMenuMapper = 
			(SysMenuMapper)BeanFactory.getBean("sysMenuMapper");
	
	private static DataService mysqlDataService = 
			(DataService)BeanFactory.getBean("mysqlDataService");
	
	/**
	 * ajax单条数据插入 
	 * url:'http://ip/rest/sysmenu' 
	 * data:'{"key1":"value1","key2":"value2",...}' 
	 * type:’POST
	 */
	@RequestMapping(value="",method=RequestMethod.POST)
	@ResponseBody
	public ReturnInfo insert( @RequestBody SysMenu info,HttpServletRequest req) {
		try {
			if(info!=null&&info.getParentId()==null){
				log.info("parent is system");
				info.setParentId(new BigDecimal(0));
			}
			sysMenuMapper.insert(info);
			return ReturnInfo.Success;
		} catch (Exception e) {
			log.warn("SysMenuCtrl insert error..",e);
//			e.printStackTrace();
		}
		return ReturnInfo.Faild;
	}
	
	/**
	 * ajax精确查询请求 
	 * url: 'http://ip/app/sysmenu/?query=({"key1":"value1","key2":"value2",...})' 
	 * dataType: 'json' 
	 * type: 'get'
	 * 
	 * ajax无条件查询全部请求
	 * url: 'http://ip/app/sysmenu' 
	 * dataType: 'json' 
	 * type: 'get' 
	 * 
	 * ajax模糊查询请求 
	 * url: 'http://ip/app/sysmenu/?query={"(colname)":{"$regex":"(colvalue)","$options":"i"} 
	 * dataType: 'json' 
	 * type: 'get'
	 * 
	 * ajax分页查询
	 * 请求 url: 'http://ip/app/sysmenu/?query=(空或{"key1":"value1","key2":"value2",...})&skip=" + beginRow + "&limit=" + rowNum' 
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
			dc.setEntityClass(SysMenu.class);
			dc.setKeyClass(SysMenuKey.class);
			dc.setMapperClass(SysMenuMapper.class);
			if(info!=null){
				String infoString =  info.getNode().toString();
				if(StringUtils.isNotEmpty(infoString)&&infoString.contains("menuName")){
					String infotmp = infoString.substring(infoString.indexOf("\"menuName\":{\"$regex\":\"")+"\"menuName\":{\"$regex\":\"".length(),infoString.indexOf("\",\"$options\"", infoString.indexOf("\"menuName\":{\"$regex\":\"")+"\"menuName\":{\"$regex\":\"".length()) );
					String infoentmp = URLDecoder.decode(infotmp, "utf-8");
					infoString = infoString.replaceAll(infotmp, infoentmp);
					ObjectMapper mapper = new ObjectMapper(); 
					JsonNode rootNode = mapper.readTree(infoString);
					info.setNode(rootNode);
				}
			}
			dc.setQmb(info);
			dc.setPageinfo(para);
			dc.setFmb(fmb);
			this.setTableName(dc);
			String sql = SqlMaker.getReferenceCountSql(dc);
			totalCount = mysqlDataService.getCount(sql);
			sql = SqlMaker.getReferenceData(dc);
			sql = sql.replaceAll("T_SYS_MENU", "(select * from t_sys_menu start with parent_id = 0 connect by  parent_id = prior menu_id)");
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
			log.warn("SysMenuCtrl get error..",e);
		}
		if(para.isPage()){
			return new ListInfo<>(totalCount, list, para);
		}else{
			return list;
		}
	}
	
	/**
	 * ajax根据ID主键查询
	 * 请求 url: 'http://ip/app/sysmenu/(_id值)' 
	 * dataType: 'json' 
	 * type: 'get'
	 */
	@RequestMapping(value="/{key:.*}",method=RequestMethod.GET)
	@ResponseBody
	public ListInfo<SysMenu> get(@PathVariable String key,HttpServletRequest req) {
		int totalCount = 1;
		List<SysMenu> list = null;
		try {
			SysMenu akey = new SysMenu();
				
				Field keyField=FieldUtils.allDeclaredField(SysMenuKey.class).get(0);

				if(keyField.getType().isInstance(1)){
					FieldUtils.setObjectValue(akey, keyField, Integer.parseInt(key));
				}else if(keyField.getType().isInstance(1L)){
					FieldUtils.setObjectValue(akey, keyField, Long.parseLong(key));
				}else{
					FieldUtils.setObjectValue(akey, keyField, key);
				}
				
			
			if(true && akey.getMenuId() == null ){
				list = new ArrayList<SysMenu>();
			}else{
				SysMenuExample example = new SysMenuExample();
				example.createCriteria().andMenuIdEqualTo(akey.getMenuId());
				list = sysMenuMapper.selectByExample(example);
			}
			totalCount = list.size();
		} catch (Exception e) {
			log.warn("SysMenuCtrl get by key error..",e);
		}
		return  new ListInfo<>(totalCount, list, 0, 1);
	}
	
	/**
	 * ajax根据主键删除 
	 * url:'http://ip/app/sysmenu/(_id值)' 
	 * type: 'DELETE' 
	 * dataType: 'json' 
	 */
	@RequestMapping(value="/{key:.*}",method=RequestMethod.DELETE)
	@ResponseBody
	public ReturnInfo delete(@PathVariable String key,HttpServletRequest req) {
		try {
			SysMenu akey = new SysMenu();
				
				Field keyField=FieldUtils.allDeclaredField(SysMenuKey.class).get(0);

				if(keyField.getType().isInstance(1)){
					FieldUtils.setObjectValue(akey, keyField, Integer.parseInt(key));
				}else if(keyField.getType().isInstance(1L)){
					FieldUtils.setObjectValue(akey, keyField, Long.parseLong(key));
				}else if(keyField.getType().isInstance(new BigDecimal("1"))){
					FieldUtils.setObjectValue(akey, keyField, new BigDecimal(key));
				}else{
					FieldUtils.setObjectValue(akey, keyField, key);
				}
				
			if(true && akey.getMenuId() != null ){
				sysMenuMapper.deleteByPrimaryKey(akey);
				return ReturnInfo.Success;
			}
		} catch (Exception e) {
			log.warn("SysMenuCtrl delete by key error..");
		}
		return ReturnInfo.Faild;
	}
	
	/**
	 * ajax根据主键单条修改 
	 * url:'http://ip/app/sysmenu/(_id值)' 
	 * data:'{"key1":"value1","key2":"value2",...}' 
	 * type:'PUT'
	 */
	@RequestMapping(value="/{key:.*}",method=RequestMethod.PUT)
	@ResponseBody
	public ReturnInfo update(@PathVariable String key,@RequestBody SysMenu info,HttpServletRequest req) {
		try {
			if(info!=null){
				SysMenu akey = new SysMenu();
				
				Field keyField=FieldUtils.allDeclaredField(SysMenuKey.class).get(0);

				if(keyField.getType().isInstance(1)){
					FieldUtils.setObjectValue(akey, keyField, Integer.parseInt(key));
				}else if(keyField.getType().isInstance(1L)){
					FieldUtils.setObjectValue(akey, keyField, Long.parseLong(key));
				}else if(keyField.getType().isInstance(new BigDecimal("1"))){
					FieldUtils.setObjectValue(akey, keyField, new BigDecimal(key));
				}else{
					FieldUtils.setObjectValue(akey, keyField, key);
				}
				SysMenuExample example = new SysMenuExample();
				example.createCriteria().andMenuIdEqualTo(akey.getMenuId());
				sysMenuMapper.updateByExampleSelective(info, example);
			}
			return ReturnInfo.Success;
		} catch (Exception e) {
			log.warn("SysMenu update by key error..");
		}
		return ReturnInfo.Faild;
	}
	
	private void setTableName(DbCondi dc){
		String tName = DBBean.getTableName2Class(SysMenu.class);
		if(dc.getOther() == null){
			Map<String,Object> o = new HashMap<String,Object>();
			o.put(SqlMaker.TABLE_NAME, tName);
			dc.setOther(o);
		}else{
			dc.getOther().put(SqlMaker.TABLE_NAME, tName);
		}
	}
	
	@SuppressWarnings("serial")
	public static class SysMenus extends ArrayList<SysMenu> {  
	    public SysMenus() { super(); }  
	}
}