package com.micro.services;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/exchgval")
public class ExchgController {

	@GetMapping("/from/{from}/to/{to}")
	public ExchgVal get(@PathVariable Currencies from, @PathVariable Currencies to) {
		ExchgVal exchgVal = null;

		if (Currencies.USD == from && Currencies.INR == to) {
			exchgVal = new ExchgVal(1l, from, to, 80.5);
		} else if (Currencies.USD == from && Currencies.YEN == to) {
			exchgVal = new ExchgVal(1l, from, to, 105.5);
		}

		return exchgVal;
	}

}
