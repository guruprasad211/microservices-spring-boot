package com.micro.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

	List<Inventory> inventory = new ArrayList<>();

	@GetMapping("/{productId}")
	public Inventory get(@PathVariable Long productId) {
		Inventory info = findInventoryInfo(productId);
		return info;
	}

	private Inventory findInventoryInfo(Long productId) {
		populateInventoryInfo();
		for(Inventory info : inventory) {
			if(info.getProductId().equals(productId)) {
				return new Inventory(info.getId(), info.getProductId(), info.getAvailable());
			}
		}
		throw new RuntimeException("Element not found");
	}

	private void populateInventoryInfo() {
		inventory.add(new Inventory(1l, 1l, true));
		inventory.add(new Inventory(2l, 2l, false));
		inventory.add(new Inventory(3l, 3l, false));
		inventory.add(new Inventory(4l, 4l, true));
		inventory.add(new Inventory(5l, 5l, false));
	}
}
