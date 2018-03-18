package com.med.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.med.dao.RoleDao;
import com.med.dao.UserDao;
import com.med.entity.Role;
import com.med.entity.User;

@Service
@Transactional
public class UserService {

	@Autowired
	UserDao userDao;
	
	@Autowired
	RoleDao roleDao;
	
	/**
	 * 获取角色
	 * @param rolesSet
	 * @return
	 */
	private String getRolesList(
			Set<Role> rolesSet) {
		StringBuilder roles = new StringBuilder();
		for (Role role : rolesSet) {
			roles.append(role.getName()).append(",");
		}	
		return roles.substring(0, roles.length() - 1);
	}
	
	/**
	 * 将User实体包装为Map
	 * @param user
	 * @return
	 */
	private Map<String, Object> getUserMap(User user) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", user.getId());
		map.put("username", user.getUsername());
		map.put("name", user.getName());
		map.put("roles", getRolesList(user.getRoles()));
		map.put("state", user.getState());
		
		return map;
	}
	
	/**
	 * 获取User实体的条件语句
	 * @param condition
	 * @return
	 */
	private String getUserCondition(String condition) {
		return condition.replaceAll(" id ", " u.id ")
				.replaceAll(" username ", " u.username ")
				.replaceAll(" name ", " u.name ")
				.replace(" state ", " u.state");
	}
	
	/**
	 * 查找所有符合条件的用户信息
	 * @param condition
	 * @return
	 */
	public List<Map<String, Object>> findUsers(String condition) {
		List<Map<String, Object>> list = 
				new ArrayList<Map<String,Object>>();
		for (User user : userDao.findAll(getUserCondition(condition))) {
			list.add(getUserMap(user));
		}
		
		return list;
	}
	
	/**
	 * 分页查找所有符合条件的用户信息
	 * @param condition
	 * @param startIndex 本页第一条记录索引
	 * @param recordNum 本页记录数
	 * @return
	 */
	public List<Map<String, Object>> findUsersByPaging(String condition,
			int startIndex, int recordNum) {
		List<Map<String, Object>> list = 
				new ArrayList<Map<String,Object>>();
		for (User user : userDao.findAllByPaging(
				getUserCondition(condition), 
				startIndex, recordNum)) {
			list.add(getUserMap(user));
		}
		
		return list;
	}
	
	/**
	 * 查找一个用户
	 * @param id
	 * @return
	 */
	public Map<String, Object> findUser(Integer id) {
		if (id == null)
			return null;
		
		User user = userDao.findOne(id);
		return (user == null ? null : getUserMap(user));
	}
	
	/**
	 * 获取角色散列
	 * @param rolesList
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private Set<Role> getRolesSet(String rolesList) {
		Set<Role> rolesSet = new HashSet<Role>();
		String[] roles = rolesList.split(",");
		for (int i = 0; i < roles.length; ++ i) {
			Role role = roleDao.findAll("WHERE name = '" 
					+ roles[i] + "'").get(0);
			rolesSet.add(role);
		}
		return rolesSet;
	}
	
	/**
	 * 
	 * @param state
	 * @return
	 */
	private Boolean getStateFromString(String state) {
		if (!state.equals("1") && !state.equals("0"))
			return null;
		
		return (state.equals("1") ? true : false);
	}
	
	/**
	 * 保存一条用户信息
	 * @param user
	 */
	public void saveUser(Map<String, Object> userMap) {
		
		User user = new User();
		user.setId(Integer.valueOf((String) userMap.get("id")));
		user.setUsername((String) userMap.get("username"));
		user.setName((String) userMap.get("name"));
		user.setRoles(getRolesSet((String) userMap.get("roles")));
		user.setState(getStateFromString((String) userMap.get("state")));
		
		if (user.getId() == null) {
			user.setPassword(user.getUsername()); // 设置默认密码为登录名
			userDao.insert(user);
		} else {
			userDao.update(user);
		}
	}
	
	/**
	 * 删除一条用户信息
	 * @param id
	 * @return
	 */
	public boolean delete(Integer id) {
		if (id == null)
			return false;
		
		userDao.delete(id);
		return true;
	}
}
