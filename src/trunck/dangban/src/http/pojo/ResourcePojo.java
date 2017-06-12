package http.pojo;

import java.util.List;

public class ResourcePojo {
	private String name;
	private String port;
	private String code;
	private String file;
	private List<BusinessPojo> busList;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	public String getFile() {
		return file;
	}
	public void setFile(String file) {
		this.file = file;
	}
	public List<BusinessPojo> getBusList() {
		return busList;
	}
	public void setBusList(List<BusinessPojo> busList) {
		this.busList = busList;
	}
	
}
