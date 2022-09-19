package com.services.fraud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("api/v1/fraud-check")
public class FraudCheckController {
	
	@Autowired
	private FraudCheckService fraudCheckService;
	
	@GetMapping(path="/{custId}")
	public FradCheckResponse isFraudster(@PathVariable Integer custId) {
		log.info("In isFraudster {}", custId);
		
		return new FradCheckResponse(fraudCheckService.isFradulantCustomer(custId));
		
	}
}
