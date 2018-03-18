package com.med.service;

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
public class MedicineServiceTest {

	@Autowired
	MedicineService medicineService;
	
	@Test
	public void findMedicines() {
		for (Medicine medicine : medicineService.findMedicines(null))
			System.out.println(medicine.getName());
	}
	
	@Test
	public void update() {
		Medicine medicine = new Medicine();
		medicine.setName("茅台");
	}
}
