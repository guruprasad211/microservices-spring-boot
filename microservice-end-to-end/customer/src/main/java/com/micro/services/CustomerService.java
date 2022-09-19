package com.micro.services;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CustomerService {

	private final CustomerRepository customerRepository;
	private final RestTemplate restTemplate;

	public void register(CustomerRequest request) {
		Customer customer = Customer.builder().firstName(request.firstName()).lastName(request.lastName())
				.email(request.email()).build();
		customerRepository.saveAndFlush(customer);

		// check if the customer is fraudster
		FradCheckResponse response = restTemplate.getForObject(
				// To connect to Eureka server service registry use below,
				// FRAUD is the name of the client application in upper case
				"http://FRAUD/api/v1/fraud-check/{custId}",
				// "http://localhost:9091/api/v1/fraud-check/{custId}",
				FradCheckResponse.class, customer.getId());

		// check if the customer is fraudster
		restTemplate.postForObject(
				// To connect to Eureka server service registry use below,
				// FRAUD is the name of the client application in upper case
				"http://NOTIFICATION/api/v1/notification/",
				// "http://localhost:9091/api/v1/fraud-check/{custId}",
				NotificationRequest.builder()
				.custId(customer.getId())
				.message("Sending message sample")
				.build(), Boolean.class);
		if (response.isFraudster()) {
			throw new IllegalStateException("Fraudster");
		}
	}

}
