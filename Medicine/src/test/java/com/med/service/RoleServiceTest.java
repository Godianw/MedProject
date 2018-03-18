package com.med.service;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.med.config.RootConfig;
import com.med.dao.RoleDao;
import com.med.entity.Privilege;
import com.med.entity.Role;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {RootConfig.class})
public class RoleServiceTest {

	@Autowired
	RoleService roleService;
	
	@Test
	public void findAll() {
		
	/*	for (Role role : roleService.findRoles(null)) {
			System.out.println(role.getName());
			if (role.getPrivileges() != null) {
				for (Privilege privilege : role.getPrivileges()) {
					System.out.println(privilege.getContent());
				}
			}
		}*/
	}
	
	@Test
	public void findOne() {
	/*	Role role = roleService.findRole(5);
		System.out.println(role.getName());
		for (Privilege privilege : role.getPrivileges()) {
			System.out.println(privilege.getContent());
		}*/
	}
	
	@Test
	public void saveRole() {
		Role role = new Role();
		role.setName("小偷");
		role.setDesc("无描述");
		roleService.saveRole(role);
		findAll();
	}
	
	@Test
	public void deleteRole() {
		roleService.deleteRole(1);
		findAll();
	}
	
	@Test
	public void savePriv() {
	/*	Set<Privilege> privileges = new HashSet<Privilege>();
		privileges.add(roleService.findPrivilege(1));
		
		roleService.saveRolePriv(1, privileges);
		findAll();*/
	}
	
	@Test
	public void findPrivilege() {
	/*	List<Map<String, Object>> list = roleService.findPrivileges(2);
		for (Map<String, Object> map : list) {
			System.out.println(map.get("id"));
			System.out.println(map.get("content"));
			System.out.println(map.get("include"));
		}*/
	}
}
