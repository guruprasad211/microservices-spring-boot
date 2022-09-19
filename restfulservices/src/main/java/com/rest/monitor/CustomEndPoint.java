package com.rest.monitor;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.stereotype.Component;

@Endpoint(id = "custom")
@Component
public class CustomEndPoint {
	
	@ReadOperation
	//public Map<String, String> customEndPoint(){
	//PAssing Query parameters to endpoints
	public Map<String, String> customEndPoint(String userName){
		Map<String, String> keys = new HashMap<>();
		keys.put("key", "value");
		keys.put("UserName", userName);
		return keys;
	}
}
