package com.med.dao;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Repository;

import com.med.entity.Privilege;
import com.med.entity.Role;

@Repository
public class RoleDao extends BaseDao {

	/**
	 * 查找所有符合条件的记录
	 * @param condition
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Role> findAll(String condition) {
		return currentSession().createQuery("FROM Role "
				+ (condition == null ? "" : condition)).list();
	}
	
	/**
	 * 分页查找所有符合条件的记录
	 * @param condition
	 * @param startIndex 第一条记录的索引
	 * @param recordNum 总记录数
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Role> findAllByPaging(String condition,
			int startIndex, int recordNum) {
		return currentSession().createQuery("FROM Role "
				+ (condition == null ? "" : condition))
				.setFirstResult(startIndex)
				.setMaxResults(startIndex + recordNum).list();
	}
	
	/**
	 * 查找所有权限
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Privilege> findAllPrivileges(String condition) {
		return currentSession().createQuery("FROM Privilege "
				+ (condition == null ? "" : condition)).list();
	}
	
	/**
	 * 查找一条记录
	 * @param id
	 * @return
	 */
	public Role findOne(int id) {
		return currentSession().get(Role.class, id);
	}
	
	/**
	 * 查找权限
	 * @param id
	 * @return
	 */
	public Privilege findOnePriv(int id) {
		return currentSession().get(Privilege.class, id);
	}
	
	/**
	 * 插入一条新记录
	 * @param role
	 * @return
	 */
	public Integer insert(Role role) {
		return (Integer) currentSession().save(role);
	}
	
	/**
	 * 更新角色
	 * @param role
	 */
	public void update(Role role) {
		Role tmpRole = findOne(role.getId());
		
		tmpRole.setName(role.getName());
		tmpRole.setDesc(role.getDesc());
		
		currentSession().update(tmpRole);
	}
	
	/**
	 * 删除一条记录
	 * @param id
	 */
	public void delete(int id) {
		Role role = findOne(id);
		currentSession().delete(role);
	}
	
	/**
	 * 更新权限
	 * @param id
	 * @param privileges
	 */
	public void updatePriv(int id, Set<Privilege> privileges) {
		Role role = findOne(id);
		role.setPrivileges(privileges);
	}
	
	/**
	 * 查找某个角色拥有的所有权限
	 * @param priv_id
	 * @return
	 */
	public Set<Privilege> findRolePrivileges(int role_id) {
		return findOne(role_id).getPrivileges();
	}
	
	/**
	 * 查询角色是否包含该权限
	 * @param role_id
	 * @param priv_id
	 * @return
	 */
	public boolean hasPrivilege(int role_id, int priv_id) {
		Set<Privilege> privileges = findRolePrivileges(role_id);
		return privileges.contains(findOnePriv(priv_id));
	}
	
	/**
	 * 查找所有的角色名
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Role> findAllRoleName() {
		return currentSession().createQuery("select id,name FROM Role")
				.list();
	}
}
