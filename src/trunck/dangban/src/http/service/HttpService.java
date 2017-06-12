package http.service;

import http.pojo.BusinessPojo;
import http.pojo.HttpPojo;

public interface HttpService {
	public String service(HttpPojo pojo,BusinessPojo buspojo);
}
