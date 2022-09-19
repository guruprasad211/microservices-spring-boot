package com.rest.monitor;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class DataBaseService implements HealthIndicator{
	
	private final String DATA_BASE_SERVICE = "DataBaseService";
	@Override
	public Health health() {
		if(isDabaBaseHealthGood()) {
			return Health.up().withDetail(DATA_BASE_SERVICE, "Service is Running").build();
		}
		return Health.down().withDetail(DATA_BASE_SERVICE, "Service is not available").build();
	}
	private boolean isDabaBaseHealthGood() {
		//logic
		return true;
	}

}
