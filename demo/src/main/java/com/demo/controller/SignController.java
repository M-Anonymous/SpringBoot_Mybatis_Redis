package com.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import com.demo.pojo.SimpleUser;
import com.demo.serviceImpl.SimpleUserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Random;
import java.util.Map;
import java.util.HashMap;
import com.zhenzi.sms.ZhenziSmsClient;

@Controller
public class SignController {
	
	@Autowired
	private SimpleUserServiceImpl userService;
	
	@GetMapping("/sign")
	public String sign() {
		return "sign";
	}
	
	@GetMapping("/info")
	public String getInfo() {
		return "info";
	}
	
	@PostMapping("/signup")
	public void signup(@RequestParam(value="verifycode") String code, HttpServletRequest request, 
			HttpServletResponse response, SimpleUser user) throws Exception {
		Map<String,Object> data = new HashMap<>();
		String verifycode = (String) request.getSession().getAttribute("verifyCode");
		if(code.equals(verifycode)){
			userService.addSimpleUser(user);
			data.put("result","注册成功");
		}else {
			data.put("result", "验证码错误");
		}
		response.setContentType("application/json;charset=utf-8");
		response.getWriter().write(new ObjectMapper().writeValueAsString(data));
	}
	
	@PostMapping("/sendsms")
	public void sendSMS(HttpServletRequest request, HttpServletResponse response, String phone) throws Exception {
			String verifyCode = String.valueOf(new Random().nextInt(899999) + 100000);
			request.getSession().setAttribute("verifyCode", verifyCode);
			ZhenziSmsClient client = new ZhenziSmsClient("https://sms_developer.zhenzikj.com", "666666", "88888888");
			Map<String,Object> params = new HashMap<>();
			params.put("number", phone);
			params.put("templateId","781");
			String[] templateParams = new String[2];
			templateParams[0] = verifyCode;
			templateParams[1] = "3";
			params.put("templateParams",templateParams);
			String result = client.send(params);
			Map<String,Object> data = new HashMap<>();
			data.put("result", result);
			response.setContentType("application/json;charset=utf-8");
			response.getWriter().write(new ObjectMapper().writeValueAsString(data));
		}
}
