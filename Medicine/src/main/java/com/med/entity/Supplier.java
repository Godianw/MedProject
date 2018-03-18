package com.med.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_supplier")
public class Supplier {
	
	/**
	 * 主键
	 * 映射列名supplier_id
	 * 不使用延迟加载
	 * id自动生成(主键自增)
	 */
	@Id
	@Column(name = "supplier_id")
	@Basic(fetch = FetchType.EAGER)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	/**
	 * 映射列名supplier_name
	 * 延迟加载
	 */
	@Column(name = "supplier_name")
	@Basic(fetch = FetchType.LAZY)
	private String name;
	
	@Column(name = "supplier_contacts")
	@Basic(fetch = FetchType.LAZY)
	private String contacts;
	
	@Column(name = "supplier_contact_phone")
	@Basic(fetch = FetchType.LAZY)
	private String contactPhone;
	
	@Column(name = "supplier_city")
	@Basic(fetch = FetchType.LAZY)
	private String city;
	
	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getContacts() {
		return contacts;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public String getCity() {
		return city;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setContacts(String contacts) {
		this.contacts = contacts;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public void setCity(String city) {
		this.city = city;
	}
	
	
}
