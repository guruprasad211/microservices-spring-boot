package com.services.fraud;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class FraudCheckService {
	@Autowired
	FraudRepository fraudRepository;
	
	public boolean isFradulantCustomer(Integer custId) {
		fraudRepository.save(FraudCheckHistory.builder()
				.isFraudster(false)
				.custId(custId)
				.createdDt(LocalDateTime.now())
				.build()
				);
		log.info("isFradulantCustomer check for custId {}", custId);
		return false;
	}

}
