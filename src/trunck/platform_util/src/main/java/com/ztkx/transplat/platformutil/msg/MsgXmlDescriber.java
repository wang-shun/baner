package com.ztkx.transplat.platformutil.msg;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 报文配置的全流程描述
 * @author zhangxiaoyun
 */
public class MsgXmlDescriber {
	static Logger logger = Logger.getLogger(MsgXmlDescriber.class);
	private String id ;
	private List<String> include = new ArrayList<String>();
	private List<Field> list = new ArrayList<Field>();
	private Map<String, Field> virtualMap = new HashMap<>();
	private CompositField cf = null;
	private String filePaht = null;		//当前describer映射的xml文件
	
	
	public String getFilePaht() {
		return filePaht;
	}
	public void setFilePaht(String filePaht) {
		this.filePaht = filePaht;
	}
	
	public List<String> getInclude() {
		return include;
	}
	public void setInclude(String include) {
		this.include.add(include);
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public List<Field> getList() {
		return list;
	}
	public void setList(List<Field> list) {
		for (Field field : list) {
			addField(field);
		}
	}
	/**
	 * 添加field字段
	 * @param field
	 * 2016年3月7日 下午3:45:02
	 * @author zhangxiaoyun
	 */
	public void addField(Field field){
		list.add(field);
		if(field.getFieldFormat().getType().equals(MsgConstantField.ATTR_TYPE_VIRTUAL)){
			logger.debug("virtual field key ["+field.getName()+"_"+field.getFieldFormat().getLevel()+"]");
			virtualMap.put(field.getName()+"_"+field.getFieldFormat().getLevel(),field);
		}
	}

	public Field getVirtualField(String name, int level) {
		return virtualMap.get(name + "_" + level);
	}

	public CompositField getCf() {
		return cf;
	}
	public void setCf(CompositField cf) {
		this.cf = cf;
	}

	@Override
	public String toString() {
		return "MsgXmlDescriber{" +
				"id='" + id + '\'' +
				", include=" + include +
				", list=" + list +
				", virtualMap=" + virtualMap +
				", cf=" + cf +
				", filePaht='" + filePaht + '\'' +
				'}';
	}
}
