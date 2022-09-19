package com.rest.filter;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomeAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	private final String CONTENT_TYPE = "application/json";
	private final AuthenticationManager authenticationManager;

	public CustomeAuthenticationFilter(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");

		log.info("attemptAuthentication username {} and apssword {}", userName, password);

		UsernamePasswordAuthenticationToken authenticateToken = new UsernamePasswordAuthenticationToken(userName,
				password);

		return authenticationManager.authenticate(authenticateToken);
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authentication) throws IOException, ServletException {
		User user = (User) authentication.getPrincipal();

		log.info("successfulAuthentication username {}", user.getUsername());
		Algorithm algo = Algorithm.HMAC256("secret123".getBytes());

		String accessToken = JWT.create().withSubject(user.getUsername())
				.withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 100))
				.withIssuer(request.getRequestURL().toString())
				.withClaim("roles",
						user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
				.sign(algo);

		String refreshToken = JWT.create().withSubject(user.getUsername())
				.withExpiresAt(new Date(System.currentTimeMillis() + 30 * 60 * 100))
				.withIssuer(request.getRequestURL().toString()).sign(algo);

		/*
		 * response.setHeader("access_token", accessToken);
		 * response.setHeader("refresh_token", refreshToken);
		 */
		Map<String, String> headers = new HashMap<>();
		headers.put("access_token", accessToken);
		headers.put("refresh_token", refreshToken);
		response.setContentType(CONTENT_TYPE);
		new ObjectMapper().writeValue(response.getOutputStream(), headers);
	}

	/* To handle login failures */
	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException failed) throws IOException, ServletException {
		log.info("unsuccessfulAuthentication username {}", request.getParameter("userName"));
		super.unsuccessfulAuthentication(request, response, failed);
	}
}
