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
@Table(name = "tbl_staff")
public class Staff {

	@Id
	@Column(name = "staff_id")
	@Basic(fetch = FetchType.EAGER)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "staff_name")
	@Basic(fetch = FetchType.LAZY)
	private String name;
	
	@Column(name = "staff_phone")
	@Basic(fetch = FetchType.LAZY)
	private String phone;
	
	@Column(name = "staff_post")
	@Basic(fetch = FetchType.LAZY)
	private String post;
	
	@Column(name = "staff_state")
	@Basic(fetch = FetchType.LAZY)
	private Boolean state;

	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getPhone() {
		return phone;
	}

	public String getPost() {
		return post;
	}

	public Boolean getState() {
		return state;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setPost(String post) {
		this.post = post;
	}

	public void setState(Boolean state) {
		this.state = state;
	}
	
	
}
