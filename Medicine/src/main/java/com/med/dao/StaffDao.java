package com.med.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.med.entity.Staff;

/**
 * 员工Dao类
 * @author Runtime
 * @date 2018/2/6
 * @version v1.0
 */
@Repository
public class StaffDao extends BaseDao {

	/**
	 * 查找所有符合条件的记录
	 * @param condition 条件
	 * @return 记录集合
	 */
	@SuppressWarnings("unchecked")
	public List<Staff> finaAll(String condition) {
		return currentSession().createQuery("FROM Staff "
				+ (condition == null ? "" : condition)).list();
	}
	
	/**
	 * 查找员工名称集合
	 * @return
	 */
	public List findAllName() {
		return currentSession()
				.createQuery("select id,name FROM Staff")
				.list();
	}
	
	/**
	 * 根据Id查找单个记录
	 * @param id
	 * @return 单个记录
	 */
	public Staff findOne(int id) {
		return currentSession().get(Staff.class, id);
	}
	
	/**
	 * 插入新记录
	 * @param medicine 员工对象
	 * @return 成功插入后的id值
	 */
	public Integer insert(Staff staff) {
		return (Integer) currentSession().save(staff);
	}
	
	/**
	 * 更新记录
	 * @param medicine 员工对象
	 */
	public void update(Staff staff) {
		// 从Session中取出对象更新
		Staff tmp = findOne(staff.getId());
		
		tmp.setName(staff.getName());
		tmp.setPhone(staff.getPhone());
		tmp.setPost(staff.getPost());
		tmp.setState(staff.getState());
		
		currentSession().update(tmp);
	}
	
	/**
	 * 删除一条记录
	 * @param id 
	 */
	public void delete(int id) {
		Staff staff = findOne(id);
		
		currentSession().delete(staff);
	}
	
	/**
	 * 查找符合条件的分页记录
	 * @param condition 条件
	 * @param startIndex 该页的起始索引
	 * @param pageSize 一页的记录数
	 * @return 记录集合
	 */
	@SuppressWarnings("unchecked")
	public List<Staff> findByPaging(String condition, 
			int startIndex, int recordNum) {
		return currentSession().createQuery("FROM Staff " + 
				(condition == null ? "" : condition))
				.setFirstResult(startIndex)
				.setMaxResults(startIndex + recordNum).list();
	}
}
