package com.med.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.med.service.UserService;

@Controller
public class IndexController {
	
	@Autowired
	UserService userService;

	@RequestMapping("/index/main.do")
	public String index() {
		return "index";
	}
	
	@RequestMapping("/err403.do")
	public String err403() {
		return "error/403";
	}
	
	@RequestMapping("/personalInfo.do")
	public String personalInfo(HttpSession session, Model model) {
		Object userObject = session.getAttribute("user");
		if (userObject != null)
			model.addAttribute("user", userObject);
		return "personalInfo";
	}
	
	@RequestMapping("*.do")
	public String err404() {
		return "error/404";
	}
}
