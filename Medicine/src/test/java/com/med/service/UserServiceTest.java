package com.med.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javassist.expr.NewArray;

import javax.transaction.Transactional;

import net.sf.json.JSONObject;

import org.hibernate.SessionFactory;
import org.hibernate.jdbc.Work;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.med.config.RootConfig;
import com.med.dao.RoleDao;
import com.med.dao.UserDao;
import com.med.entity.Role;
import com.med.entity.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {RootConfig.class})
@Transactional
public class UserServiceTest {

	@Autowired 
	UserService userService;
	
	@Autowired
	UserDao userDao;
	
	@Autowired
	RoleDao roleDao;
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Test
	public void find() {
		User user = userService.findUserByUsernameAndPassword(
				"admin0", "admin01");
		System.out.println(user);
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
		sessionFactory.getCurrentSession().doWork(new Work() {
			
			@Override
			public void execute(Connection connection) 
					throws SQLException {
				// TODO 自动生成的方法存根
				System.out.println(connection+"vxcv");
			}
		});
	}
}
