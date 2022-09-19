package com.micro.services;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Inventory {
	private Long id;
	private Long productId;
	private Boolean available;
	
	
}
