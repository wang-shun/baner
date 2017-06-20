package com.ztkx.transplat.platformutil.context;

import com.ztkx.transplat.platformutil.context.imp.CommonSDO;

import java.io.Serializable;
import java.util.Map;

/**
 * 交易上下文公共接口
 * 
 * @author zhangxiaoyun
 *
 */
public interface CommonContext extends Serializable {

	// 本容器作用域
	public static final String SCOPE_LOCAL = "local_container";

	// 全局作用域
	public static final String SCOPE_GLOBAL = "global_container";

	// 容器初始化容量
	public static final String INIT_SIZE = "100";

	/**
	 * 完成上下文容器的初始化 当context pool不够用的时候使用
	 */
	public void init(int sequence);

	/**
	 * 普通初始化使用
	 */
	public void init();

	/**
	 * 内部克隆的时候使用
	 */
	public void initClone();

	/**
	 * 是否为系统启动的时候创建
	 * 
	 * @return
	 */
	public boolean isInit();

	/**
	 * 是否为系统启动的时候创建
	 * 
	 * @param isInit
	 */
	public void setInit(boolean isInit);

	/**
	 * 深度克隆,如果容器内部存储的是对象类型，那么该对象 的克隆为浅克隆
	 * 
	 * @param sourceContext
	 *            被克隆对象
	 * @return 克隆结果
	 */
	public CommonContext clone();

	/**
	 * 克隆全局变量和SDO
	 * 
	 * @return
	 */
	public CommonContext cloneGlobal();

	/**
	 * 从指定区域已对象的形式获取值
	 * 
	 * @param key
	 * @param scope
	 *            区域
	 * @return
	 */
	public Object getObj(String key, String scope);

	/**
	 * 默认从SCOPE_LOCAL区域获取值
	 * 
	 * @param key
	 * @return
	 */
	public Object getObj(String key);

	/**
	 * 把对象注入到指定区域，set方法判断key是否已存在如果存在不做任何处理直接返回
	 * 
	 * @param key
	 * @param val
	 * @param scope
	 */
	public void setObj(String key, Object val, String scope);

	/**
	 * 默认将对象注入到SCOPE_LOCAL区域，set方法判断key是否已存在如果存在不做任何处理直接返回
	 * 
	 * @param key
	 * @param val
	 */
	public void setObj(String key, Object val);

	/**
	 * 以String的形式从指定区域返回值
	 * 
	 * @param key
	 * @param scope
	 * @return
	 */
	public String getFieldStr(String key, String scope);

	/**
	 * 默认以String的形式从SCOPE_LOCAL中获取值
	 * 
	 * @param key
	 * @return
	 */
	public String getFieldStr(String key);

	/**
	 * 将字符串注入到指定区域，set方法判断key是否已存在如果存在不做任何处理直接返回
	 * 
	 * @param key
	 * @param val
	 * @param scope
	 */
	public void setFieldStr(String key, String val, String scope);

	/**
	 * 将字符串注入到SCOPE_LOCAL区域，set方法判断key是否已存在如果存在不做任何处理直接返回
	 * 
	 * @param key
	 * @param val
	 */
	public void setFieldStr(String key, String val);

	/**
	 * 以byte数组的形式获取对象
	 * 
	 * @param key
	 * @param scope
	 * @return
	 */
	public byte[] getByteArray(String key, String scope);

	public byte[] getByteArray(String key);

	public void setByteArray(String key, byte[] val);

	public void setByteArray(String key, byte[] val, String scope);

	/**
	 * 获取指定区域的容器
	 * 
	 * @param scope
	 * @return
	 */
	public Map<String, Object> getContainer(String scope);

	/**
	 * 释放上下文资源
	 */
	public void clear();

	/**
	 * 获取交易SDO信息
	 * 
	 * @return
	 */
	public CommonSDO getSDO();

	/**
	 * 获取当前context的序列号
	 * 
	 * @return
	 */
	public int getSequence();

	@Override
	public String toString();

	public void setSdo(CommonSDO sdo);

	public void remove(String key, String scope);

	public void remove(String key);

	public void setErrorCode(String key);

	public String getErrorCode();
	
	public void setResponseCode(String key);

	public String getResponseCode();

	/**
	 * 获取原始报文
	 * 
	 * @return
	 */
	public String getOrginalField();

	/**
	 * 获取原始报文
	 * 
	 * @return
	 */
	public void setOrginalField(String value);
	/**
	 * 获取context中globl容器
	 * @return
	 */
	public Map<String, Object> getGlobalContainer();
	/**
	 * 获取context中local容器
	 * @return
	 */
	public Map<String, Object> getLocalContainer();

	/**
	 * 获取响应报文
	 * @return
	 */
//	public String getResMsgField();

	/**
	 * 在当前context中注入响应报文
	 * @param value
	 */
//	public void setResMsgField(String value);

	/**
	 * 从指定区域已对象的形式获取值
	 *
	 * @param key
	 * @param scope
	 *            区域
	 * @return
	 */
	public Integer getInt(String key, String scope);

	/**
	 * 默认从SCOPE_LOCAL区域获取值
	 *
	 * @param key
	 * @return
	 */
	public Integer getInt(String key);
	/**
	 * 从指定区域已对象的形式获取值
	 *
	 * @param key
	 * @param scope
	 *            区域
	 * @return
	 * String key, byte[] val, String scope
	 */
	public void setInt(String key, Integer val,String scope);

	/**
	 * 默认从SCOPE_LOCAL区域获取值
	 *
	 * @param key
	 * @return
	 */
	public void setInt(String key, Integer val);

	public <T> T getBean(Class<T> clazz);
}
