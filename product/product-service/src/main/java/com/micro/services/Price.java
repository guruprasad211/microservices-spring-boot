package com.micro.services;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Price {
	private Long id;
	private Long productId;
	private Double originalPrice;
	private Double discountPrice;
	
	
}
