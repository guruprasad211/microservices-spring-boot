package com.rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rest.services.entities.AppUser;

@Repository
public interface UserRepository extends JpaRepository<AppUser, Long>{
	
	AppUser findByUserName(String userName);
	
}
