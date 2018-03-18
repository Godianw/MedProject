package com.med.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("index")
public class IndexController {

	@RequestMapping("/main.do")
	public String index() {
		return "index";
	}
	
	@RequestMapping("/login.do")
	public String login() {
		return "login";
	}
}
