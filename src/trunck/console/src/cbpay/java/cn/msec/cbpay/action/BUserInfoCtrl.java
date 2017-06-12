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

import cn.msec.cbpay.entity.BUserInfo;
import cn.msec.cbpay.entity.BUserInfoExample;
import cn.msec.cbpay.entity.BUserInfoKey;
import cn.msec.cbpay.mapper.BUserInfoMapper;
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
@RequestMapping("/buserinfo")
public class BUserInfoCtrl extends BasicCtrl {

	private static BUserInfoMapper bUserInfoMapper = 
			(BUserInfoMapper)BeanFactory.getBean("bUserInfoMapper");
	
	private static DataService mysqlDataService = 
			(DataService)BeanFactory.getBean("mysqlDataService");
	
	/**
	 * ajax单条数据插入 
	 * url:'http://ip/rest/buserinfo' 
	 * data:'{"key1":"value1","key2":"value2",...}' 
	 * type:’POST
	 */
	@RequestMapping(value="",method=RequestMethod.POST)
	@ResponseBody
	public ReturnInfo insert( @RequestBody BUserInfo info,HttpServletRequest req) {
		try {
			bUserInfoMapper.insert(info);
			return ReturnInfo.Success;
		} catch (Exception e) {
			log.warn("BUserInfoCtrl insert error..",e);
//			e.printStackTrace();
		}
		return ReturnInfo.Faild;
	}
	
	/**
	 * ajax精确查询请求 
	 * url: 'http://ip/app/buserinfo/?query=({"key1":"value1","key2":"value2",...})' 
	 * dataType: 'json' 
	 * type: 'get'
	 * 
	 * ajax无条件查询全部请�?	 * url: 'http://ip/app/buserinfo' 
	 * dataType: 'json' 
	 * type: 'get' 
	 * 
	 * ajax模糊查询请求 
	 * url: 'http://ip/app/buserinfo/?query={"(colname)":{"$regex":"(colvalue)","$options":"i"} 
	 * dataType: 'json' 
	 * type: 'get'
	 * 
	 * ajax分页查询
	 * 请求 url: 'http://ip/app/buserinfo/?query=(空或{"key1":"value1","key2":"value2",...})&skip=" + beginRow + "&limit=" + rowNum' 
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
			dc.setEntityClass(BUserInfo.class);
			dc.setKeyClass(BUserInfoKey.class);
			dc.setMapperClass(BUserInfoMapper.class);
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
			log.warn("BUserInfoCtrl get error..",e);
		}
		if(para.isPage()){
			return new ListInfo<>(totalCount, list, para);
		}else{
			return list;
		}
	}
	
	/**
	 * ajax根据ID主键查询
	 * 请求 url: 'http://ip/app/buserinfo/(_id�?' 
	 * dataType: 'json' 
	 * type: 'get'
	 */
	@RequestMapping(value="/{key:.*}",method=RequestMethod.GET)
	@ResponseBody
	public ListInfo<BUserInfo> get(@PathVariable String key,HttpServletRequest req) {
		int totalCount = 1;
		List<BUserInfo> list = null;
		try {
			BUserInfo akey = new BUserInfo();
				
				Field keyField=FieldUtils.allDeclaredField(BUserInfoKey.class).get(0);

				if(keyField.getType().isInstance(1)){
					FieldUtils.setObjectValue(akey, keyField, Integer.parseInt(key));
				}else if(keyField.getType().isInstance(1L)){
					FieldUtils.setObjectValue(akey, keyField, Long.parseLong(key));
				}else{
					FieldUtils.setObjectValue(akey, keyField, key);
				}
				
				
			
			if(true && akey.getPurchaserid() == null  && akey.getMerchantid() == null ){
				list = new ArrayList<BUserInfo>();
			}else{
				BUserInfoExample example = new BUserInfoExample();
				example.createCriteria().andPurchaseridEqualTo(akey.getPurchaserid()).andMerchantidEqualTo(akey.getMerchantid());
				list = bUserInfoMapper.selectByExample(example);
			}
			totalCount = list.size();
		} catch (Exception e) {
			log.warn("BUserInfoCtrl get by key error..",e);
		}
		return  new ListInfo<>(totalCount, list, 0, 1);
	}
	
	/**
	 * ajax根据主键删除 
	 * url:'http://ip/app/buserinfo/(_id�?' 
	 * type: 'DELETE' 
	 * dataType: 'json' 
	 */
	@RequestMapping(value="/{key:.*}",method=RequestMethod.DELETE)
	@ResponseBody
	public ReturnInfo delete(@PathVariable String key,HttpServletRequest req) {
		try {
			BUserInfo akey = new BUserInfo();
				
				Field keyField=FieldUtils.allDeclaredField(BUserInfoKey.class).get(0);

				if(keyField.getType().isInstance(1)){
					FieldUtils.setObjectValue(akey, keyField, Integer.parseInt(key));
				}else if(keyField.getType().isInstance(1L)){
					FieldUtils.setObjectValue(akey, keyField, Long.parseLong(key));
				}else{
					FieldUtils.setObjectValue(akey, keyField, key);
				}
				
				
			if(true && akey.getPurchaserid() != null  && akey.getMerchantid() != null ){
				bUserInfoMapper.deleteByPrimaryKey(akey);
				return ReturnInfo.Success;
			}
		} catch (Exception e) {
			log.warn("BUserInfoCtrl delete by key error..");
		}
		return ReturnInfo.Faild;
	}
	
	/**
	 * ajax根据主键单条修改 
	 * url:'http://ip/app/buserinfo/(_id�?' 
	 * data:'{"key1":"value1","key2":"value2",...}' 
	 * type:'PUT'
	 */
	@RequestMapping(value="/{key:.*}",method=RequestMethod.PUT)
	@ResponseBody
	public ReturnInfo update(@PathVariable String key,@RequestBody BUserInfo info,HttpServletRequest req) {
		try {
			if(info!=null){
				BUserInfo akey = new BUserInfo();
				
				Field keyField=FieldUtils.allDeclaredField(BUserInfoKey.class).get(0);

				if(keyField.getType().isInstance(1)){
					FieldUtils.setObjectValue(akey, keyField, Integer.parseInt(key));
				}else if(keyField.getType().isInstance(1L)){
					FieldUtils.setObjectValue(akey, keyField, Long.parseLong(key));
				}else{
					FieldUtils.setObjectValue(akey, keyField, key);
				}
				
				BUserInfoExample example = new BUserInfoExample();
				example.createCriteria().andPurchaseridEqualTo(akey.getPurchaserid()).andMerchantidEqualTo(akey.getMerchantid());
				bUserInfoMapper.updateByExampleSelective(info, example);
			}
			return ReturnInfo.Success;
		} catch (Exception e) {
			log.warn("BUserInfo update by key error..");
		}
		return ReturnInfo.Faild;
	}
	
	private void setTableName(DbCondi dc){
		String tName = DBBean.getTableName2Class(BUserInfo.class);
		if(dc.getOther() == null){
			Map<String,Object> o = new HashMap<String,Object>();
			o.put(SqlMaker.TABLE_NAME, tName);
			dc.setOther(o);
		}else{
			dc.getOther().put(SqlMaker.TABLE_NAME, tName);
		}
	}
	
	@SuppressWarnings("serial")
	public static class BUserInfos extends ArrayList<BUserInfo> {  
	    public BUserInfos() { super(); }  
	}
}