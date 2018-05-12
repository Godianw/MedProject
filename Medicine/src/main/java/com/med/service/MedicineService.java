package com.med.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.med.dao.MedicineDao;
import com.med.dao.SupplierDao;
import com.med.entity.Medicine;
import com.med.exception.DataInvalidException;

@Service
@Transactional
public class MedicineService {

	@Autowired
	MedicineDao medicineDao;
	
	@Autowired
	SupplierDao supplierDao;
	
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
	
	/**
	 * 批量插入
	 * @param list
	 * @return
	 */
	public boolean batchInsert(List<List<Object>> list) 
		throws DataInvalidException {
		List<Medicine> medicines = new ArrayList<Medicine>();
		for (List<Object> med : list) {
			Medicine medicine = new Medicine();
			medicine.setName(med.get(0).toString());
			medicine.setSupplier(supplierDao.findOne
					(supplierDao.findIdByName(med.get(1).toString())));
			medicine.setBatchNum(med.get(2).toString());
			medicine.setProductDate(med.get(3).toString());
			medicine.setStorageTime(med.get(4).toString());
			medicine.setUnit(med.get(5).toString());
			medicine.setPurchasePrice(Float.valueOf(med.get(6).toString()));
			medicine.setSinglePrice(Float.valueOf(med.get(7).toString()));
			medicine.setQuantity(0);
			medicine.setWarningQuantity(100);
			medicines.add(medicine);
		}
		medicineDao.insertMedicines(medicines);
		
		return true;
	}
	
	/**
	 * 获取封装的数据集合
	 * @return
	 */
	@SuppressWarnings({ "rawtypes" })
	public List<List> getDataList() {
		List<List> dataList = new ArrayList<List>();
		List<Medicine> medicineList = findMedicines(
				"ORDER BY id DESC");
		// 将药品集合装入数组集合中
		for (Medicine medicine : medicineList) {
			List<Object> singleData = new ArrayList<Object>();
			singleData.add(medicine.getId());
			singleData.add(medicine.getName());
			singleData.add(medicine.getSupplier().getName());
			singleData.add(medicine.getBatchNum());
			singleData.add(medicine.getProductDate());
			singleData.add(medicine.getStorageTime());
			singleData.add(medicine.getUnit());
			singleData.add(medicine.getPurchasePrice());
			singleData.add(medicine.getSinglePrice());
			dataList.add(singleData);
		}
		
		return dataList;
	}
}
