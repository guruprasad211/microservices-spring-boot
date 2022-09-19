package com.micro.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/products")
public class ProductController {

	List<ProductInfo> prodInfo = new ArrayList<>();
	
	@Autowired
	RestTemplate template;

	@GetMapping("/{id}")
	public Product get(@PathVariable Long id) {
		ProductInfo info = findProductInfo(id);
		Object[] uri = { id };

		Price price = template.getForObject("http://localhost:8082/prices/"+id, Price.class);

		Inventory inv = template.getForObject("http://localhost:8083/inventory/"+id, Inventory.class);
		return new Product(id, info.getName(), info.getDesc(), price.getOriginalPrice(), inv.getAvailable());
	}

	private ProductInfo findProductInfo(Long id) {
		populateProductInfo();
		for (ProductInfo info : prodInfo) {
			if (info.getId().equals(id)) {
				return new ProductInfo(id, info.getName(), info.getDesc());
			}
		}
		throw new RuntimeException("Element not found");
	}

	private void populateProductInfo() {
		prodInfo.add(new ProductInfo(1l, "IPhone", "Iphone 13"));
		prodInfo.add(new ProductInfo(2l, "Book", "Micro services"));
		prodInfo.add(new ProductInfo(3l, "Mobile", "Android"));
		prodInfo.add(new ProductInfo(4l, "Kids", "Games"));
		prodInfo.add(new ProductInfo(5l, "Cars", "car Accessaries"));
	}
}
