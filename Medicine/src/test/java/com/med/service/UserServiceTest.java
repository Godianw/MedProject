package com.med.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javassist.expr.NewArray;

import javax.transaction.Transactional;

import net.sf.json.JSONObject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.med.config.RootConfig;
import com.med.dao.UserDao;
import com.med.entity.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {RootConfig.class})
@Transactional
public class UserServiceTest {

	@Autowired
	UserService userService;
	
	@Autowired
	UserDao userDao;
	
	@Test
	public void find() {
		
	}
	
	@Test
	public void findAll() {
		for (Map<String, Object> user : userService.findUsers(
				"join u.roles r where r.id = 4 ORDER BY u.id desc")) {
			System.out.println(user.get("username"));
			System.out.println(user.get("name"));
		}
	}
	
	@Test
	public void findOne() {
		Map<String, Object> user = userService.findUser(1);
		System.out.println(user.get("username"));
		System.out.println(user.get("name"));
	}
	
	@Test
	public void save() {
		Map<String, Object> userMap = new HashMap<String, Object>();
	//	userMap.put("id", 1);
		userMap.put("username", "user000x");
		userMap.put("name", "李静");
		userMap.put("state", "0");
		userMap.put("roles", "管理员");
		
		userService.saveUser(userMap);
	}
	
	@Test
	public void delete() {
		userService.delete(1);
		System.out.println(userService.findUsers(null).size());
		
	}
	
	@Test
	public void json() {
	/*	String json = "{\"username\":\"Tom\",\"name\":\"name\",\"roles\":\"管理员\",\"state\":\"1\"}";
		JSONObject obj = JSONObject.fromObject(json);
		Map map = obj;
		System.out.println("username = " + map.get("username"));*/
		Date date = new Date();
		System.out.println(date);
	}
}
