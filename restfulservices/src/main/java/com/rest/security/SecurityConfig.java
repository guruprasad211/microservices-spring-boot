package com.rest.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.rest.filter.CustomAutherizationFilter;
import com.rest.filter.CustomeAuthenticationFilter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Slf4j
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private static final String[] AUTH_WHITELIST = {
			// -- Swagger UI v2
			"/v2/api-docs", "/swagger-resources", "/swagger-resources/**", "/configuration/ui",
			"/configuration/security", "/swagger-ui.html", "/webjars/**",
			// -- Swagger UI v3 (OpenAPI)
			"/v3/api-docs/**", "/swagger-ui/**", "/h2/**", "/login*", "/logout*", "/token/refresh/**"
			// other public endpoints of your API may be appended to this array
	};

	private final UserDetailsService userDetailsService;
	private final BCryptPasswordEncoder passwordEncoder;

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		log.info("configure AuthenticationManagerBuilder");
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
	}

	// INSERT INTO USER VALUES(1,'admin',
	// '$2a$10$fDJAzi6F1/YBW0ZcbimDneru7daQKsGwxCgRgkGfgAKukEuv4zpA.', 'admin')
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		log.info("configure HttpSecurity");
		CustomeAuthenticationFilter customAuthenticationFilter = new CustomeAuthenticationFilter(authenticationManagerBean());
		//to set custom login url
		//customAuthenticationFilter.setFilterProcessesUrl("/api/login");
		
		http.csrf().disable();
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		http.authorizeRequests()
				.antMatchers(AUTH_WHITELIST).permitAll()
				.antMatchers("/api/**").authenticated()
				.and().headers().frameOptions().sameOrigin();
		http.httpBasic();
		http.addFilter(customAuthenticationFilter);
		http.addFilterBefore(new CustomAutherizationFilter(), UsernamePasswordAuthenticationFilter.class);
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
}
