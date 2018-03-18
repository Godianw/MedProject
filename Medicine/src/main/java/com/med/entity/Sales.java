package com.med.entity;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_sales")
public class Sales {

	/**
	 * 主键
	 * 映射列名sales_id
	 * 不使用延迟加载
	 * id自动生成(主键自增)
	 */
	@Id
	@Column(name = "sales_id")
	@Basic(fetch = FetchType.EAGER)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne(cascade = {CascadeType.REFRESH}, optional = false)
	@JoinColumn(name = "sales_staff_id")
	private Staff staff;
	
	@ManyToOne(cascade = {CascadeType.REFRESH}, optional = false)
	@JoinColumn(name = "sales_med_id")
	private Medicine medicine;
	
	@Column(name = "sales_count")
	@Basic(fetch = FetchType.LAZY)
	private Integer count;
	
	@Column(name = "sales_datetime")
	@Basic(fetch = FetchType.LAZY)
	private String datetime;
	
	@Column(name = "sales_money")
	@Basic(fetch = FetchType.LAZY)
	private Float money;

	public Integer getId() {
		return id;
	}

	public Staff getStaff() {
		return staff;
	}

	public Medicine getMedicine() {
		return medicine;
	}

	public Integer getCount() {
		return count;
	}

	public String getDatetime() {
		return datetime;
	}

	public Float getMoney() {
		return money;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setStaff(Staff staff) {
		this.staff = staff;
	}

	public void setMedicine(Medicine medicine) {
		this.medicine = medicine;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}

	public void setMoney(Float money) {
		this.money = money;
	}
	
}