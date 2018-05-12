package com.med.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.transaction.Transactional;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import redis.clients.jedis.Jedis;

import com.med.config.RootConfig;
import com.med.dao.MedicineDao;
import com.med.dao.RoleDao;
import com.med.dao.SupplierDao;
import com.med.entity.Medicine;
import com.med.entity.Privilege;
import com.med.entity.Role;
import com.med.util.ExcelUtil;
import com.med.util.ExcelUtil.ExcelSuffix;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {RootConfig.class})
public class RoleServiceTest {

	@Autowired
	RoleService roleService;
	
	@Autowired
	MedicineDao medicineDao;
	
	@Autowired 
	SupplierDao supplierDao;
	
	@Autowired
	MedicineService medicineService;
	
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
	public void findPrivilege() throws EncryptedDocumentException, InvalidFormatException, IOException {
	/*	List<Map<String, Object>> list = roleService.findPrivileges(2);
		for (Map<String, Object> map : list) {
			System.out.println(map.get("id"));
			System.out.println(map.get("content"));
			System.out.println(map.get("include"));
		}*/
		ExcelUtil excelUtil = new ExcelUtil();
		System.out.println("before import, the size is " + medicineDao.finaAll(null).size());
		List<Medicine> medicines = new ArrayList<Medicine>();
		for (List<Object> list : excelUtil.readFromExcel(null)) {
	//		System.out.println(supplierDao.findIdByName(list.get(1).toString()));
			Medicine medicine = new Medicine();
			medicine.setName(list.get(0).toString());
			medicine.setSupplier(supplierDao.findOne
					(supplierDao.findIdByName(list.get(1).toString())));
			medicine.setBatchNum(list.get(2).toString());
			medicine.setProductDate(list.get(3).toString());
			medicine.setStorageTime(list.get(4).toString());
			medicine.setPurchasePrice(Float.valueOf(list.get(5).toString()));
			medicine.setSinglePrice(Float.valueOf(list.get(6).toString()));
			medicines.add(medicine);
		}
		medicineDao.insertMedicines(medicines);
		
		System.out.println("after import, the size is " + medicineDao.finaAll(null).size());
	}
}
