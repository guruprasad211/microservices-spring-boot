package com.rest.services.controllers;

import java.net.URI;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rest.services.UserService;
import com.rest.services.entities.AppUser;
import com.rest.services.entities.Role;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = "/api/users")
@RequiredArgsConstructor
@Validated
@Slf4j
public class UserController {

	private static final String CONTENT_TYPE = "application/json";
	@Autowired
	private final UserService userService;

	//@GetMapping(value = "/", produces = "application/json")
	@GetMapping
	public ResponseEntity<List<AppUser>> getUsers() {
		return ResponseEntity.ok().body(userService.findUsers());
	}

	@GetMapping(value = "/{userName}", produces = "application/json")
	public AppUser findUser(@PathVariable String userName) {
		return userService.findUser(userName);
	}

	@PostMapping
	public ResponseEntity<AppUser> create(@RequestBody AppUser user) {
		URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentRequestUri().path("/api/users").toUriString());
		return ResponseEntity.created(uri).body(userService.saveUser(user));
	}

	@PostMapping(path = "/role")
	public ResponseEntity<Role> create(@RequestBody Role role) {
		URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentRequestUri().path("/api/users/role").toUriString());
		return ResponseEntity.created(uri).body(userService.saveRole(role));
	}

	@PostMapping(path = "/role/addRoleToUser")
	public ResponseEntity<Role> addRoleToUser( @RequestBody RoleToUserForm roleToUser) {
		userService.addRoleToUser(roleToUser.getUserName(), roleToUser.getRoleName());
		return ResponseEntity.ok().build();
	}
	
	@GetMapping(value = "/token/refresh", produces = "application/json")
	public void refreshToken(HttpServletRequest request, HttpServletResponse response) {
		String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
		if (authHeader != null && authHeader.startsWith("Bearer ")) {
			try {
				String refreshToken = authHeader.substring("Bearer ".length());
				Algorithm algo = Algorithm.HMAC256("secret123".getBytes());
				JWTVerifier verifier = JWT.require(algo).build();
				DecodedJWT decodeJWT = verifier.verify(refreshToken);
				String userName = decodeJWT.getSubject();
				AppUser user = userService.findUser(userName);

				String accessToken = JWT.create().withSubject(user.getUserName())
						.withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 100))
						.withIssuer(request.getRequestURL().toString())
						.withClaim("roles",
								user.getRoles().stream().map(Role::getName).collect(Collectors.toList()))
						.sign(algo);
				
				Map<String, String> headers = new HashMap<>();
				headers.put("access_token", accessToken);
				headers.put("refresh_token", refreshToken);
				response.setContentType(CONTENT_TYPE);
				new ObjectMapper().writeValue(response.getOutputStream(), headers);
			} catch (Exception e) {
				log.error("Authorization failed {}", e.getMessage());
				throw new AccessDeniedException("Authorization Denied!!");
			}
		}else {
			throw new RuntimeException("Refresh token is missing");
		}
	}

	@Data
	private class RoleToUserForm {
		private String userName;
		private String roleName;
	}
}
