package com.rest.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomAutherizationFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
		log.info("CustomAutherizationFilter doFilterInternal {}", authHeader);
		if ((!request.getServletPath().equals("/login") && !request.getServletPath().equals("/token/refresh"))
				&& authHeader != null && authHeader.startsWith("Bearer ")) {
			try {
				String token = authHeader.substring("Bearer ".length());
				Algorithm algo = Algorithm.HMAC256("secret123".getBytes());
				JWTVerifier verifier = JWT.require(algo).build();
				DecodedJWT decodeJWT = verifier.verify(token);
				String userName = decodeJWT.getSubject();
				String[] roles = decodeJWT.getClaim("roles").asArray(String.class);
				Collection<SimpleGrantedAuthority> authorities = Arrays.stream(roles)
						.map(role -> new SimpleGrantedAuthority(role)).collect(Collectors.toList());
				UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userName,
						null, authorities);
				SecurityContextHolder.getContext().setAuthentication(authToken);
				filterChain.doFilter(request, response);
			} catch (Exception e) {
				log.error("Authorization failed {}", e.getMessage());
				throw new AccessDeniedException("Authorization Denied!!");
			}
		}

		filterChain.doFilter(request, response);
	}

}
