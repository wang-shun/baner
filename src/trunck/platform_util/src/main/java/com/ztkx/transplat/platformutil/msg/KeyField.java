package com.ztkx.transplat.platformutil.msg;

import java.util.List;

public class KeyField {
	List<Field> list = null;

	public KeyField() {
	}
	

	public KeyField(List<Field> list) {
		this.list = list;
	}


	public List<Field> getList() {
		return list;
	}

	public void setList(List<Field> list) {
		this.list = list;
	}


	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < list.size(); i++) {
			sb.append(list.get(i).toString());
		}
		return "KeyField [list=" + sb.toString() + "]";
	}
	
}
