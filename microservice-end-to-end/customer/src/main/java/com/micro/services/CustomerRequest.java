package com.micro.services;

public record CustomerRequest(
		String firstName, 
		String lastName, 
		String email) {

}
