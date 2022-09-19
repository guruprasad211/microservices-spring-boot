package com.rest.services;

import java.util.List;

import com.rest.services.entities.Role;
import com.rest.services.entities.AppUser;

public interface UserService {

	AppUser saveUser(AppUser user);

	Role saveRole(Role role);

	void addRoleToUser(String userName, String roleName);

	AppUser findUser(String userName);

	List<AppUser> findUsers();

}
