package com.med.dao;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Repository;

import com.med.entity.Role;
import com.med.entity.User;

@Repository
public class UserDao extends BaseDao {
	
	/**
	 * 查找所有符合条件的记录
	 * @param condition
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<User> findAll(String condition) {
		System.out.println(condition);
		return currentSession().createQuery("select u FROM User u "
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
	public List<User> findAllByPaging(String condition,
			int startIndex, int recordNum) {
		System.out.println(condition);
		return currentSession().createQuery("select u FROM User u "
				+ (condition == null ? "" : condition))
				.setFirstResult(startIndex)
				.setMaxResults(recordNum).list();
	}
	
	/**
	 * 查找单个用户
	 * @param id
	 * @return
	 */
	public User findOne(int id) {
		return currentSession().get(User.class, id);
	}
	
	/**
	 * 添加一条记录
	 * @param user
	 * @return
	 */
	public Integer insert(User user) {
		return (Integer) currentSession().save(user);
	}
	
	/**
	 * 更新一个用户
	 * @param user
	 */
	public void update(User user) {
		User tmpUser = findOne(user.getId());
		
		tmpUser.setUsername(user.getUsername());
		tmpUser.setName(user.getName());
		tmpUser.setRoles(user.getRoles());
		tmpUser.setState(user.getState());
		
		currentSession().update(tmpUser);
	}
	
	/**
	 * 删除一个用户
	 * @param id
	 */
	public void delete(int id) {
		User user = findOne(id);
		currentSession().delete(user);
	}
	
	/**
	 * 更新用户角色
	 * @param id
	 * @param roles
	 */
	public void updateRoles(int id, Set<Role> roles) {
		User user = findOne(id);
		
		user.setRoles(roles);
		currentSession().update(user);
	}
}
