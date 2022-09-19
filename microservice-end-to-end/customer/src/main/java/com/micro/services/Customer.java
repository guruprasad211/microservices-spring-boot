package com.micro.services;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
@Entity
public class Customer {
	@Id
	@SequenceGenerator(name = "cust_id_seq", sequenceName = "cust_id_seq")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cust_id_seq")
	private Integer id;
	private String firstName;
	private String lastName;
	private String email;
}
