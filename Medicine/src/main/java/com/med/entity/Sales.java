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

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

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
	
	/**
	 * 操作人的用户名
	 */
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "sales_user_id")
	@Fetch(value = FetchMode.JOIN)
	private User user;
	
	/**
	 * 销售药品
	 */
	@ManyToOne(cascade = {CascadeType.REFRESH})
	@JoinColumn(name = "sales_med_id")
	@Basic(fetch = FetchType.LAZY)
	private Medicine medicine;
	
	/**
	 * 销售数量
	 */
	@Column(name = "sales_count")
	@Basic(fetch = FetchType.LAZY)
	private Integer count;
	
	/**
	 * 销售日期
	 */
	@Column(name = "sales_datetime")
	@Basic(fetch = FetchType.LAZY)
	private String datetime;
	
	/**
	 * 总金额
	 */
	@Column(name = "sales_money")
	@Basic(fetch = FetchType.LAZY)
	private Float money;

	public Integer getId() {
		return id;
	}

	public User getUser() {
		return user;
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

	public void setUser(User user) {
		this.user = user;
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
