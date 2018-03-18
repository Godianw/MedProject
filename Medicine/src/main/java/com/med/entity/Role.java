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
@Table(name = "tbl_role")
public class Role {

	/**
	 * 主键
	 * 映射列名role_id
	 * 不使用延迟加载
	 * id自动生成(主键自增)
	 */
	@Id
	@Column(name = "role_id")
	@Basic(fetch = FetchType.EAGER)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "role_name")
	@Basic(fetch = FetchType.LAZY)
	private String name;
	
	@Column(name = "role_desc")
	@Basic(fetch = FetchType.LAZY)
	private String desc;
	
	@ManyToMany
	@Cascade(CascadeType.REFRESH)
	@JoinTable(
			name = "tbl_user_role",
			joinColumns = {@JoinColumn(name = "ur_role_id")},
			inverseJoinColumns = {@JoinColumn(name = "ur_user_id")})
	private Set<User> users = new HashSet<User>();
	
	/**
	 * CascadeType.SAVE_UPDATE表示使用hibernate注解级联保存和更新
	 * JoinColumns定义本方在中间表的主键映射
	 * inverseJoinColumns定义另一在中间表的主键映射
	 */
	@ManyToMany
	@Cascade(CascadeType.SAVE_UPDATE)
	@JoinTable(
			name = "tbl_role_priv",
			joinColumns = {@JoinColumn(name = "rp_role_id")},		
			inverseJoinColumns = {@JoinColumn(name = "rp_priv_id")})
	private Set<Privilege> privileges = new HashSet<Privilege>();

	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getDesc() {
		return desc;
	}

	public Set<User> getUsers() {
		return users;
	}

	public Set<Privilege> getPrivileges() {
		return privileges;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	public void setPrivileges(Set<Privilege> privileges) {
		this.privileges = privileges;
	}

	
}
