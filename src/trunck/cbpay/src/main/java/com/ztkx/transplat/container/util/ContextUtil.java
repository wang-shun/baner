package com.ztkx.transplat.container.util;

import java.util.Map;

import org.apache.log4j.Logger;

import com.ztkx.transplat.platformutil.context.CommonContext;

/**
 * context 操作工具
 * @author zhangxiaoyun
 * 2016年1月16日 下午3:59:21
 * <p>Company:ztkx</p>
 */
public class ContextUtil {
	static Logger logger = Logger.getLogger(ContextUtil.class);
	/**
	 * 将source context中的所有属性复制到target中
	 * @param source
	 * @param target
	 */
	public static void contextCopy(CommonContext source,CommonContext target){
		Map<String, Object> globl = source.getGlobalContainer();
		if(globl!=null && globl.size()!=0){
			/**
			 * 挨个将globl中的数据拷贝到target中去
			 */
			for (Map.Entry<String, Object> entry: globl.entrySet()) {
				target.setObj(entry.getKey(), entry.getValue(),CommonContext.SCOPE_GLOBAL);
			}
		}
		
		Map<String, Object> local = source.getLocalContainer();
		if(local!=null && local.size()!=0){
			/**
			 * 挨个将local中的数据拷贝到target中去
			 */
			for (Map.Entry<String, Object> entry: local.entrySet()) {
				target.setObj(entry.getKey(), entry.getValue(),CommonContext.SCOPE_LOCAL);
			}
		}
		
		/**
		 * 拷贝SDO
		 */
		target.setSdo(source.getSDO().clone());
	}
	
	/**
	 * 设置错误码
	 * @param errorCode
	 * @param context
	 */
	public static void setErrorCode(String errorCode,CommonContext context){
		if(context.getErrorCode()==null){
			context.setErrorCode(errorCode);
		}
	}
	
	
	public static void setResponseCode(String errorCode,CommonContext context){
		if(context.getResponseCode()==null){
			context.setResponseCode(errorCode);
		}
	}
}
