package com.med.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.med.dao.MedicineDao;
import com.med.entity.Medicine;

@Service
@Transactional
public class MedicineService {

	@Autowired
	MedicineDao medicineDao;
	
	/**
	 * 查找所有符合条件的药品
	 * @param condition 条件
	 * @return 药品集合
	 */
	public List<Medicine> findMedicines(String condition) {
		System.out.println(condition);
		return medicineDao.finaAll(condition);
	}
	
	/**
	 * 根据起始索引和记录数查找符合条件的药品记录
	 * @param condition 条件
	 * @param startIndex 起始索引
	 * @param recordNum 记录数
	 * @return 药品集合
	 */
	public List<Medicine> findMedicinesByPaging(String condition,
			int startIndex, int recordNum) {
		System.out.println(condition);
		return medicineDao.findByPaging(condition, startIndex, recordNum);
	}
	
	/**
	 * 根据id查找对应的药品
	 * @param id
	 * @return 药品
	 */
	public Medicine findOne(Integer id) {
		if (id == null) 
			return null;
		else
			return medicineDao.findOne(id);
	}
	
	/**
	 * 更新药品信息
	 * @param supplier 药品对象
	 */
	public void save(Medicine medicine) {
		
		if (medicine.getId() == null) {
			// 设置默认的库存值和库存预警值
			medicine.setQuantity(0);
			medicine.setWarningQuantity(100);
			medicineDao.insert(medicine);
		} else {
			medicineDao.update(medicine);
		}
	}
	
	/**
	 * 删除药品
	 * @param id
	 * @return
	 */
	public boolean delete(Integer id) {
		
		if (id != null) {
			medicineDao.delete(id);
			return true;
		}
		return false;
	}
	
	/**
	 * 查找药品名称集合
	 * @return
	 */
	public List<Medicine> findMedicineNameList() {
		
		List<Medicine> resultList = new ArrayList<Medicine>();
		Iterator<?> iterator = medicineDao.findAllName().iterator();
		while (iterator.hasNext()) {
			Object[] obj = (Object[]) iterator.next();
			Medicine medicine = new Medicine();
			medicine.setId(Integer.valueOf(obj[0].toString()));
			medicine.setName(obj[1].toString());
			resultList.add(medicine);
		}
		
		return resultList;
	}
}
