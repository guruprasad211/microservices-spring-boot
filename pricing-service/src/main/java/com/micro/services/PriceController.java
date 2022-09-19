package com.micro.services;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/prices")
public class PriceController {

	List<Price> priceInfo = new ArrayList<>();

	@Autowired
	RestTemplate template;

	@GetMapping("/{productId}")
	public Price get(@PathVariable Long productId) {
		Price info = findPriceInfo(productId);
		return info;
	}

	private Price findPriceInfo(Long productId) {
		populateProductInfo();
		for (Price info : priceInfo) {
			if (info.getProductId().equals(productId)) {
				ExchgVal exchgVal = template.getForObject("http://localhost:8084/exchgval/from/USD/to/INR",
						ExchgVal.class);

				return new Price(info.getId(), info.getProductId(),
						 exchgVal.getExchgVal() * info.getOriginalPrice().longValue(),
						info.getDiscountPrice());
			}
		}
		throw new RuntimeException("Element not found");
	}

	private void populateProductInfo() {
		priceInfo.add(new Price(1l, 1l, 80000.50, 10000.5));
		priceInfo.add(new Price(2l, 2l, 800.50, 100.5));
		priceInfo.add(new Price(3l, 3l, 8000.50, 1000.5));
		priceInfo.add(new Price(4l, 4l, 5000.50, 50.5));
		priceInfo.add(new Price(5l, 5l, 70000.50, 10000.5));
	}
}
