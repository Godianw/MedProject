package com.med.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity
@Table(name = "tbl_user")
public class User {

	/**
	 * 主键
	 * 映射列名user_id
	 * 不使用延迟加载
	 * id自动生成(主键自增)
	 */
	@Id
	@Column(name = "user_id")
	@Basic(fetch = FetchType.EAGER)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "user_username")
	@Basic(fetch = FetchType.LAZY)
	private String username;
	
	@Column(name = "user_name")
	@Basic(fetch = FetchType.LAZY)
	private String name;
	
	@Column(name = "user_password")
	@Basic(fetch = FetchType.LAZY)
	private String password;
	
	/**
	 * CascadeType.SAVE_UPDATE表示使用hibernate注解级联保存和更新
	 * JoinColumns定义本方在中间表的主键映射
	 * inverseJoinColumns定义另一在中间表的主键映射
	 */
	@ManyToMany
	@Cascade(CascadeType.REFRESH)
	@JoinTable(name = "tbl_user_role",
			joinColumns = {@JoinColumn(name = "ur_user_id")},		
			inverseJoinColumns = {@JoinColumn(name = "ur_role_id")})
	private Set<Role> roles = new HashSet<Role>();
	
	@Column(name = "user_state")
	@Basic(fetch = FetchType.LAZY)
	private Boolean state;

	public Integer getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public String getName() {
		return name;
	}

	public String getPassword() {
		return password;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public Boolean getState() {
		return state;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public void setState(Boolean state) {
		this.state = state;
	}
	
}
