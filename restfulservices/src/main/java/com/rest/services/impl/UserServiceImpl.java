package com.rest.services.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.rest.repository.RoleRepository;
import com.rest.repository.UserRepository;
import com.rest.services.UserService;
import com.rest.services.entities.Role;
import com.rest.services.entities.AppUser;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
@AllArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {

	@Autowired
	UserRepository userRepo;

	@Autowired
	RoleRepository roleRepo;
	
	private final PasswordEncoder passwordEncoder;	

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		AppUser user = userRepo.findByUserName(username);
		if (user == null) {
			log.error("User not found in database for user {}", username);
			throw new UsernameNotFoundException("User not found");
		}

		log.info("User found in database for user {}", username);
		Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
		user.getRoles().forEach(role -> {
			authorities.add(new SimpleGrantedAuthority(role.getName()));
		});
		return new User(user.getUserName(), user.getPassword(), authorities);
	}

	@Override
	public AppUser saveUser(AppUser user) {
		log.info("Saving the user to DB {}", user.getName());
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userRepo.save(user);
	}

	@Override
	public Role saveRole(Role role) {
		log.info("Saving the role to DB {}", role.getName());
		return roleRepo.save(role);
	}

	@Override
	public void addRoleToUser(String userName, String roleName) {
		log.info("adding the user {} to role {} in DB", userName, roleName);
		AppUser user = userRepo.findByUserName(userName);
		Role role = roleRepo.findByName(roleName);

		user.getRoles().add(role);
	}

	@Override
	public AppUser findUser(String userName) {
		log.info("fetching the user for userName {} ", userName);
		return userRepo.findByUserName(userName);
	}

	@Override
	public List<AppUser> findUsers() {
		return userRepo.findAll();
	}

}
