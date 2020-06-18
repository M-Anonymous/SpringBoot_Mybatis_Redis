package com.demo.config;

import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.beans.factory.annotation.Autowired;
import com.demo.serviceImpl.SimpleUserServiceImpl;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private SimpleUserServiceImpl userDetailsService;
	@Autowired
	private MySuccessHandler successHandler;
	@Autowired
	private MyFailureHandler failHandler;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// TODO Auto-generated method stub
		auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// TODO Auto-generated method stub
		http.authorizeRequests()
		.antMatchers("/info").hasRole("USER")
		.anyRequest().permitAll()
		.and()
		.formLogin().loginPage("/sign")
		.usernameParameter("username")
		.passwordParameter("password")
		.loginProcessingUrl("/signin")
		.successHandler(successHandler)
		.failureHandler(failHandler)
		.and()
		.csrf().disable();
		
	}

}
