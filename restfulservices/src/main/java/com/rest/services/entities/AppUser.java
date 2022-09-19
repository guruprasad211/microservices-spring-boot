package com.rest.services.entities;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "USER") @Data @AllArgsConstructor @NoArgsConstructor

public class AppUser {
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String name;
	@Column(name = "user_name")
	private String userName;
	private String password;
	
	//When users are loaded, it loads roles at the same time if EAGER is the fetch type
	@ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "USER_ROLES", joinColumns = { @JoinColumn(name = "user_id") }, 
		inverseJoinColumns = { @JoinColumn(name = "role_id") })
	private Collection<Role> roles = new ArrayList<>();

	
}
