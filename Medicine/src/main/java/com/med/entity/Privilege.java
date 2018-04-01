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
@Table(name = "tbl_privilege")
public class Privilege {

	/**
	 * 主键
	 * 映射列名priv_id
	 * 不使用延迟加载
	 * id自动生成(主键自增)
	 */
	@Id
	@Column(name = "priv_id")
	@Basic(fetch = FetchType.EAGER)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "priv_content")
	@Basic(fetch = FetchType.LAZY)
	private String content;
	
	@Column(name = "priv_request_url")
	@Basic(fetch = FetchType.LAZY)
	private String requestUrl;
	
	@Column(name = "priv_pid")
	@Basic(fetch = FetchType.LAZY)
	private Integer pid;
	
	/**
	 * CascadeType.SAVE_UPDATE表示使用hibernate注解级联保存和更新
	 * JoinColumns定义本方在中间表的主键映射
	 * inverseJoinColumns定义另一在中间表的主键映射
	 */
	@ManyToMany
	@Cascade(CascadeType.SAVE_UPDATE)
	@JoinTable(
			name = "tbl_role_priv",
			joinColumns = {@JoinColumn(name = "rp_priv_id")},		
			inverseJoinColumns = {@JoinColumn(name = "rp_role_id")})
	private Set<Role> roles = new HashSet<Role>();

	public Integer getId() {
		return id;
	}

	public String getContent() {
		return content;
	}

	public String getRequestUrl() {
		return requestUrl;
	}

	public Integer getPid() {
		return pid;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setRequestUrl(String requestUrl) {
		this.requestUrl = requestUrl;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	
}
