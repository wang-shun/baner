package http.pojo;

public class BusinessPojo {
	private String name;
	private String policy;
	private String field;
	private String file;
	private int server_begin;
	private int server_length;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPolicy() {
		return policy;
	}
	public void setPolicy(String policy) {
		this.policy = policy;
	}
	
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	public int getServer_begin() {
		return server_begin;
	}
	public void setServer_begin(int server_begin) {
		this.server_begin = server_begin;
	}
	public int getServer_length() {
		return server_length;
	}
	public void setServer_length(int server_length) {
		this.server_length = server_length;
	}
	
	public String getFile() {
		return file;
	}
	public void setFile(String file) {
		this.file = file;
	}
	@Override
	public String toString() {
		return "BusinessPojo [name=" + name + ", policy=" + policy + ", field="
				+ field + ", file=" + file + ", server_begin=" + server_begin
				+ ", server_length=" + server_length + "]";
	}
	
	
}
