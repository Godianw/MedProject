package com.med.controller;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import redis.clients.jedis.Jedis;

import com.med.entity.Role;
import com.med.entity.User;
import com.med.service.RoleService;
import com.med.service.UserService;

@Controller
public class LoginController {

	@Autowired
	UserService userService;
	
	@Autowired
	RoleService roleService;
	
	@RequestMapping("/index.do")
	public String index() {
		return "login";
	}
	
	@RequestMapping("/login.do")
	public String login(String username, String password,
			Model model, HttpSession session) {
		User user = userService.findUserByUsernameAndPassword(
				username, password);
		if (user != null) {
			session.setAttribute("user", user);
			return "redirect:/index/main.do";
		}
		
		model.addAttribute("result", "用户名或密码错误");
		return "login";
	}
	
	@RequestMapping("/logout.do")
	public String logout(HttpSession session) {
		session.removeAttribute("user");
		return "redirect:/index.do";
	}
}
