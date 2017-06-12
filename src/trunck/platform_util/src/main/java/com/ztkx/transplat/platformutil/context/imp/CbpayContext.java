package com.ztkx.transplat.platformutil.context.imp;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang.StringUtils;

import com.ztkx.transplat.platformutil.baseconfig.ConstantConfigField;
import com.ztkx.transplat.platformutil.context.CommonContext;

/**
 * 上下文容器的第一个实现
 * 
 * @author zhangxiaoyun
 *
 */
public class CbpayContext implements CommonContext {

	private static final long serialVersionUID = -8898760051488920377L;
	private volatile static AtomicInteger maxSequence = new AtomicInteger(0);
	// 默认序列化的时候，忽略transient类型的属性
	private transient Map<String, Object> localContainer = null;// 本地容器
	private Map<String, Object> globalContainer = null;// 全局容器
	private boolean isInit = false;

	private int sequence = -1;// context 0 表示系统启动后创建的
	private CommonSDO sdo = null;// 交易公共属性

	public boolean isInit() {
		return isInit;
	}

	public void setInit(boolean isInit) {
		this.isInit = isInit;
	}

	@Override
	public void init(int sequence) {
		this.sequence = sequence;
		localContainer = new HashMap<String, Object>(
				Integer.parseInt(INIT_SIZE));
		globalContainer = new HashMap<String, Object>(
				Integer.parseInt(INIT_SIZE));
		sdo = new CommonSDO();
	}

	@Override
	public void init() {
		sequence = maxSequence.incrementAndGet();
		localContainer = new HashMap<String, Object>(
				Integer.parseInt(INIT_SIZE));
		globalContainer = new HashMap<String, Object>(
				Integer.parseInt(INIT_SIZE));
		sdo = new CommonSDO();
	}

	@Override
	public void initClone() {
		localContainer = new HashMap<String, Object>(
				Integer.parseInt(INIT_SIZE));
		globalContainer = new HashMap<String, Object>(
				Integer.parseInt(INIT_SIZE));
	}

	@Override
	public CommonContext clone() {
		// 初始化目标context
		CommonContext target = new CbpayContext();

		target.initClone();

		// 开始克隆本地对象数据
		Map<String, Object> sourceLocal = this.getContainer(SCOPE_LOCAL);
		for (Map.Entry<String, Object> obj : sourceLocal.entrySet()) {
			target.setObj(obj.getKey(), obj.getValue(), SCOPE_LOCAL);
		}

		// 开始克隆全局对象数据
		Map<String, Object> globalLocal = this.getContainer(SCOPE_GLOBAL);
		for (Map.Entry<String, Object> obj : globalLocal.entrySet()) {
			target.setObj(obj.getKey(), obj.getValue(), SCOPE_GLOBAL);
		}
		target.setSdo(this.sdo.clone());

		return target;
	}

	@Override
	public CommonContext cloneGlobal() {
		// 初始化目标context
		CommonContext target = new CbpayContext();

		target.initClone();

		// 开始克隆全局对象数据
		Map<String, Object> globalLocal = this.getContainer(SCOPE_GLOBAL);
		for (Map.Entry<String, Object> obj : globalLocal.entrySet()) {
			target.setObj(obj.getKey(), obj.getValue(), SCOPE_GLOBAL);
		}
		target.setSdo(this.sdo.clone());

		return target;
	}

	@Override
	public Object getObj(String key, String scope) {
		if (scope.equals(SCOPE_LOCAL)) {
			return localContainer.get(key);
		} else if (scope.equals(SCOPE_GLOBAL)) {
			return globalContainer.get(key);
		} else {
			return null;
		}
	}

	@Override
	public Object getObj(String key) {
		return getObj(key, SCOPE_LOCAL);
	}

	@Override
	public void setObj(String key, Object val, String scope) {
		// update by tiangunagzhao 2016/3/11.
		// 调用其他系统服务时，由于存在公共报文头，导致响应报文解析后无法进入存入context中
		// if (scope.equals(SCOPE_LOCAL)) {
		// if (!localContainer.containsKey(key)) {
		// localContainer.put(key, val);
		// }
		// } else if (scope.equals(SCOPE_GLOBAL)) {
		// if (!globalContainer.containsKey(key)) {
		// globalContainer.put(key, val);
		// }
		// }
		if (scope.equals(SCOPE_LOCAL)) {
			localContainer.put(key, val);
		} else if (scope.equals(SCOPE_GLOBAL)) {
			globalContainer.put(key, val);

		}
	}

	@Override
	public void setObj(String key, Object val) {
		setObj(key, val, SCOPE_LOCAL);
	}

	@Override
	public String getFieldStr(String key, String scope) {
		if (scope.equals(SCOPE_LOCAL) && localContainer.containsKey(key)) {
			return (String) localContainer.get(key);
		} else if (scope.equals(SCOPE_GLOBAL)
				&& globalContainer.containsKey(key)) {
			return (String) globalContainer.get(key);
		} else {
			return null;
		}
	}

	@Override
	public byte[] getByteArray(String key, String scope) {
		if (scope.equals(SCOPE_LOCAL) && localContainer.containsKey(key)) {
			return (byte[]) localContainer.get(key);
		} else if (scope.equals(SCOPE_GLOBAL)
				&& globalContainer.containsKey(key)) {
			return (byte[]) globalContainer.get(key);
		} else {
			return null;
		}
	}

	@Override
	public void setByteArray(String key, byte[] val, String scope) {
		setObj(key, val, scope);
	}

	@Override
	public void setByteArray(String key, byte[] val) {
		setObj(key, val, SCOPE_LOCAL);
	}

	@Override
	public String getFieldStr(String key) {
		return getFieldStr(key, SCOPE_LOCAL);
	}

	@Override
	public byte[] getByteArray(String key) {
		return getByteArray(key, SCOPE_LOCAL);
	}

	@Override
	public void setFieldStr(String key, String val, String scope) {
		setObj(key, val, scope);
	}

	@Override
	public void setFieldStr(String key, String val) {
		setObj(key, val);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("\n the context sequence [" + sequence + "] \n");
		sb.append("<local:>\n");
		if (localContainer == null || localContainer.size() == 0) {
			sb.append("\t null \n");
		} else {
			for (Map.Entry<String, Object> obj : localContainer.entrySet()) {
				sb.append("\t[" + obj.getKey() + ":" + obj.getValue() + "]\n");
			}
		}

		sb.append("\n\n");
		sb.append("<global:>\n");
		if (globalContainer == null) {
			sb.append("\t null \n");
		} else {
			for (Map.Entry<String, Object> obj : globalContainer.entrySet()) {
				sb.append("\t[" + obj.getKey() + ":" + obj.getValue() + "]\n");
			}
		}
		return sb.toString();
	}

	@Override
	public Map<String, Object> getContainer(String scope) {
		if (scope.equals(SCOPE_LOCAL)) {
			return localContainer;
		} else if (scope.equals(SCOPE_GLOBAL)) {
			return globalContainer;
		} else {
			return null;
		}
	}

	@Override
	public void setSdo(CommonSDO sdo) {
		this.sdo = sdo;
	}

	@Override
	public CommonSDO getSDO() {
		return sdo;
	}

	@Override
	public void clear() {
		if (localContainer != null) {
			localContainer.clear();
		}
		if (globalContainer != null) {
			globalContainer.clear();
		}
		if (sdo != null) {
			sdo.clear();
		}
	}

	@Override
	public int getSequence() {
		return sequence;
	}

	public void remove(String key, String scope) {
		if (scope.equals(SCOPE_LOCAL)) {
			if (localContainer.containsKey(key)) {
				localContainer.remove(key);
			}
		} else if (scope.equals(SCOPE_GLOBAL)) {
			if (globalContainer.containsKey(key)) {
				globalContainer.remove(key);
			}
		}
	}

	public void remove(String key) {
		this.remove(key, SCOPE_LOCAL);
	}

	@Override
	public void setErrorCode(String key) {
		if (StringUtils.isNotBlank(key)) {
			this.remove(ConstantConfigField.CONTAINER_ERROR_CODE, SCOPE_GLOBAL);
			this.setFieldStr(ConstantConfigField.CONTAINER_ERROR_CODE, key,SCOPE_GLOBAL);
		}
	}

	@Override
	public String getErrorCode() {
		return this.getFieldStr(ConstantConfigField.CONTAINER_ERROR_CODE,
				SCOPE_GLOBAL);
	}

	@Override
	public void setResponseCode(String key) {
		if (StringUtils.isNotBlank(key)) {
			this.remove(ConstantConfigField.CONTAINER_RESPONSE_CODE,
					SCOPE_GLOBAL);
			this.setFieldStr(ConstantConfigField.CONTAINER_RESPONSE_CODE, key,
					SCOPE_GLOBAL);
		}
	}

	@Override
	public String getResponseCode() {
		return this.getFieldStr(ConstantConfigField.CONTAINER_RESPONSE_CODE,
				SCOPE_GLOBAL);
	}

	@Override
	public String getOrginalField() {
		return getFieldStr(ConstantConfigField.ORIGINAL_MSG, SCOPE_GLOBAL);
	}

	@Override
	public void setOrginalField(String value) {
		if (StringUtils.isNotBlank(value)) {
			this.remove(ConstantConfigField.ORIGINAL_MSG, SCOPE_GLOBAL);
			this.setFieldStr(ConstantConfigField.ORIGINAL_MSG, value,
					SCOPE_GLOBAL);
		}
	}

	/*
	 * @Override public String getResMsgField() { return
	 * getFieldStr(ConstantConfigField.RETURN_MESSAGE, SCOPE_GLOBAL); }
	 */

	/*
	 * @Override public void setResMsgField(String value) { if
	 * (StringUtils.isNotBlank(value)) {
	 * this.remove(ConstantConfigField.RETURN_MESSAGE, SCOPE_GLOBAL);
	 * this.setFieldStr(ConstantConfigField.RETURN_MESSAGE, value,SCOPE_GLOBAL);
	 * } }
	 */

	@Override
	public Map<String, Object> getGlobalContainer() {
		return globalContainer;
	}

	@Override
	public Map<String, Object> getLocalContainer() {
		return localContainer;
	}

}
