package com.med.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.med.dao.RoleDao;
import com.med.entity.Privilege;
import com.med.entity.Role;

@Service
@Transactional
public class RoleService {

	@Autowired
	RoleDao roleDao;
	
	private Map<String, Object> getRoleMap(Role role) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", role.getId());
		map.put("name", role.getName());
		map.put("desc", role.getDesc());
		
		return map;
	}
	
	/**
	 * 查找所有符合条件的角色信息
	 * @param condition
	 * @return
	 */
	public List<Map<String, Object>> findRoles(String condition) {
		List<Map<String, Object>> list = 
				new ArrayList<Map<String,Object>>();
		for (Role role : roleDao.findAll(condition)) {
			list.add(getRoleMap(role));
		}
		
		return list;
	}
	
	/**
	 * 分页查找所有符合条件的角色信息
	 * @param condition
	 * @param startIndex 本页第一条记录索引
	 * @param recordNum 本页记录数
	 * @return
	 */
	public List<Map<String, Object>> findRolesByPaging(String condition,
			int startIndex, int recordNum) {
		List<Map<String, Object>> list = 
				new ArrayList<Map<String,Object>>();
		for (Role role : roleDao.findAllByPaging(
				condition, startIndex, recordNum)) {
			list.add(getRoleMap(role));
		}
		
		return list;
	}
	
	/**
	 * 查找单个角色信息
	 * @param id
	 * @return
	 */
	public Map<String, Object> findRole(Integer id) {
		if (id == null)
			return null;
		
		Role role = roleDao.findOne(id);
		return (role == null ? null : getRoleMap(role));
	}
	
	/**
	 * 查找单个角色权限
	 * @param id
	 * @return
	 */
	public Privilege findPrivilege(Integer priv_id) {
		if (priv_id == null)
			return null;
		else
			return roleDao.findOnePriv(priv_id);
	}
	
	/**
	 * 查找角色权限信息
	 * @param id
	 * @return
	 */
	public Map<String, Object> findPrivileges(Integer id) {
		List<Privilege> privileges = roleDao.findAllPrivileges();
		if (id == null || privileges == null)
			return null;
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		for (Privilege privilege : privileges) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", privilege.getId());
			map.put("content", privilege.getContent());
			map.put("include", 
					roleDao.hasPrivilege(id, privilege.getId()));
			list.add(map);
		}
		
		resultMap.put("roleId", id);
		resultMap.put("privilege", list);
		return resultMap;
	}
	
	/**
	 * 更新单个角色信息
	 * @param role
	 */
	public void saveRole(Role role) {
		if (role.getId() == null) {
			role.setPrivileges(null);
			roleDao.insert(role);
		} else {
			roleDao.update(role);
		}
	}
	
	/**
	 * 删除单个角色信息
	 * @param id
	 * @return
	 */
	public boolean deleteRole(Integer id) {
		
		if (id == null) 
			return false;
		
		roleDao.delete(id);
		
		return true;
	}
	
	/**
	 * 保存角色权限信息
	 * @param id
	 * @param privileges
	 * @return
	 */
	public boolean saveRolePriv(Integer id, String[] privIds) {
		
		if (id == null || privIds.length == 0) {
			return false;
		}
		
		// 封装权限集合
		Set<Privilege> privileges = new HashSet<Privilege>();
		for (int i = 0; i < privIds.length; ++ i) {
			privileges.add(
					findPrivilege(Integer.valueOf(privIds[i])));
		}
		
		roleDao.updatePriv(id, privileges);;
		return true;
	}
	
	/**
	 * 查找所有角色名
	 * @return
	 */
	public List<Role> findRoleNameList() {
		List<Role> rolesList = new ArrayList<Role>();
		Iterator<?> iterator = roleDao.findAllRoleName().iterator();
		while (iterator.hasNext()) {
			Object[] obj = (Object[]) iterator.next();
			Role role = new Role();
			role.setId(Integer.valueOf(obj[0].toString()));
			role.setName(obj[1].toString());
			rolesList.add(role);
		}
		
		return rolesList;
	}
}
