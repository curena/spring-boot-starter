package org.cecil.auth.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

//@Configuration
public class AuthConfig extends WebSecurityConfigurerAdapter {
//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//		http
//				.authorizeRequests(a -> a
//						.antMatchers("/", "/error", "/css/**",
//									 "/js/**", "/images/**", "/assets/**")
//						.permitAll()
//						.anyRequest().authenticated()
//				)
//				.oauth2Login();
//	}


}
