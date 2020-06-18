package com.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import com.demo.serviceImpl.SimpleUserServiceImpl;

@SpringBootTest
class DemoApplicationTests {
	
	@Autowired
	private SimpleUserServiceImpl userService;

	@Test
	void contextLoads() {
		userService.loadUserByUsername("username");
	}

}
