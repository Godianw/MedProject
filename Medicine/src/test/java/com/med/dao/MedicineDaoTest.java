package com.med.dao;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.med.config.RootConfig;
import com.med.entity.Medicine;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {RootConfig.class})
@Transactional
public class MedicineDaoTest {

	@Autowired
	MedicineDao medicineDao;
	
	@Test
	public void findAll() {
		
		for (Medicine medicine : medicineDao.finaAll(null)) {
			System.out.println(medicine.getName());
		}
	}
	
	@Test
	public void findOne() {
		
		System.out.println(medicineDao.findOne(1).getName());
	}
}
