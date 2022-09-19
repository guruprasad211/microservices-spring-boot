package com.micro.services;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExchgVal {
	private Long id;
	private Currencies from;
	private Currencies to;
	private Double exchgVal;
	
	
}
