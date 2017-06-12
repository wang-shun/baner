package com.ztkx.transplat.platformutil.msg;

import java.util.List;

/**
 * modules.xml文件中每个module的对象
 * @author zhangxiaoyun
 * 2016年3月6日 下午3:05:56
 * <p>Company:ztkx</p>
 */
public class ModulesDescriber {
	private String id ;							//module id
	private List<Field> modulesField;			//modules里面的字段
	
	public ModulesDescriber() {
		
	}
	
	public ModulesDescriber(String id, List<Field> modulesField) {
		this.id = id;
		this.modulesField = modulesField;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<Field> getModulesField() {
		return modulesField;
	}

	public void setModulesField(List<Field> modulesField) {
		this.modulesField = modulesField;
	}

	@Override
	public String toString() {
		return "ModulesDescriber [id=" + id + ", modulesField=" + modulesField
				+ "]";
	}
}
