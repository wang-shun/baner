package http.pojo;

public class HttpPojo {
	private String server;
	private String method;
	private String data;
	public String getServer() {
		return server;
	}
	public void setServer(String server) {
		this.server = server;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	@Override
	public String toString() {
		return "HttpPojo [server=" + server + ", method=" + method + ", data="
				+ data + "]";
	}
	
}
