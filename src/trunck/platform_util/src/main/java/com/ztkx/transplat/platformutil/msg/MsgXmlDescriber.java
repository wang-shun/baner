package com.ztkx.transplat.platformutil.msg;

import java.util.ArrayList;
import java.util.List;

/**
 * 报文配置的全流程描述
 * @author zhangxiaoyun
 */
public class MsgXmlDescriber {
	private String id ;
	private List<String> include = new ArrayList<String>();
	private List<Field> list = new ArrayList<Field>();
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
		this.list.addAll(list);
	}
	/**
	 * 添加field字段
	 * @param field
	 * 2016年3月7日 下午3:45:02
	 * @author zhangxiaoyun
	 */
	public void addField(Field field){
		list.add(field);
	}
	public CompositField getCf() {
		return cf;
	}
	public void setCf(CompositField cf) {
		this.cf = cf;
	}
	
	@Override
	public String toString() {
		return "MsgXmlDescriber [id=" + id + ", include=" + include + ", list="
				+ list + ", cf=" + cf + "]";
	}
	
	
}
