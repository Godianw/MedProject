package com.med.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.med.dao.StaffDao;
import com.med.entity.Staff;

/**
 * 员工服务类
 * @author Runtime
 * @date 2018/2/7
 * @version v1.0
 */
@Service
@Transactional
public class StaffService {

	@Autowired
	StaffDao staffDao;
	
	/**
	 * 查找所有符合条件的员工
	 * @param condition 条件
	 * @return 员工集合
	 */
	public List<Staff> findStaffs(String condition) {
		System.out.println(condition);
		return staffDao.finaAll(condition);
	}
	
	/**
	 * 根据起始索引和记录数查找符合条件的员工记录
	 * @param condition 条件
	 * @param startIndex 起始索引
	 * @param recordNum 记录数
	 * @return 员工集合
	 */
	public List<Staff> fingStaffsByPaging(String condition,
			int startIndex, int recordNum) {
		System.out.println(condition);
		return staffDao.findByPaging(condition, startIndex, recordNum);
	}
	
	/**
	 * 根据id查找对应的员工
	 * @param id
	 * @return 员工
	 */
	public Staff findOne(Integer id) {
		if (id == null) 
			return null;
		else
			return staffDao.findOne(id);
	}
	
	/**
	 * 更新员工信息
	 * @param supplier 员工对象
	 */
	public void save(Staff staff) {
		
		if (staff.getId() == null)
			staffDao.insert(staff);
		else
			staffDao.update(staff);
	}
	
	/**
	 * 删除员工
	 * @param id
	 * @return
	 */
	public boolean delete(Integer id) {
		
		if (id != null) {
			staffDao.delete(id);
			return true;
		}
		
		return false;
	}
	
	/**
	 * 查找员工名称集合
	 * @return
	 */
	public List<Staff> findStaffNameList() {
		
		List<Staff> resultList = new ArrayList<Staff>();
		Iterator<?> iterator = staffDao.findAllName().iterator();
		while (iterator.hasNext()) {
			Object[] obj = (Object[]) iterator.next();
			Staff staff = new Staff();
			staff.setId(Integer.valueOf(obj[0].toString()));
			staff.setName(obj[1].toString());
			resultList.add(staff);
		}
		
		return resultList;
	}
}
