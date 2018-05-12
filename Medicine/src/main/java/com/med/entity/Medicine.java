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
@Table(name = "tbl_medicine")
public class Medicine {

	/**
	 * 主键
	 * 映射列名med_id：药品编号
	 * 不使用延迟加载
	 * id自动生成(主键自增)
	 */
	@Id
	@Column(name = "med_id")
	@Basic(fetch = FetchType.EAGER)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	/**
	 * 药品名称
	 */
	@Column(name = "med_name")
	@Basic(fetch = FetchType.LAZY)
	private String name;
	
	/**
	 * 多对一映射，外键列为med_supplier_id：供应商编号
	 */
	@ManyToOne(cascade = {CascadeType.REFRESH}, optional = false)
	@JoinColumn(name = "med_supplier_id")
	private Supplier supplier;
	
	/**
	 * 生产批号
	 */
	@Column(name = "med_batch_num")
	@Basic(fetch = FetchType.LAZY)
	private String batchNum;
	
	/**
	 * 生产日期
	 */
	@Column(name = "med_product_date")
	@Basic(fetch = FetchType.LAZY)
	private String productDate;
	
	/**
	 * 保质期
	 */
	@Column(name = "med_storage_time")
	@Basic(fetch = FetchType.LAZY)
	private String storageTime;
	
	/**
	 * 进价
	 */
	@Column(name = "med_purchase_price")
	@Basic(fetch = FetchType.LAZY)
	private Float purchasePrice;
	
	/**
	 * 单价
	 */
	@Column(name = "med_single_price")
	@Basic(fetch = FetchType.LAZY)
	private Float singlePrice;
	
	/**
	 * 计量单位
	 */
	@Column(name = "med_unit")
	@Basic(fetch = FetchType.LAZY)
	private String unit;
	
	/**
	 * 库存数量
	 */
	@Column(name = "med_quantity")
	@Basic(fetch = FetchType.LAZY)
	private Integer quantity;
	
	/**
	 * 库存预警数量
	 */
	@Column(name = "med_warning_quantity")
	@Basic(fetch = FetchType.LAZY)
	private Integer warningQuantity;

	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Supplier getSupplier() {
		return supplier;
	}

	public String getBatchNum() {
		return batchNum;
	}

	public String getProductDate() {
		return productDate;
	}

	public String getStorageTime() {
		return storageTime;
	}

	public Float getPurchasePrice() {
		return purchasePrice;
	}

	public Float getSinglePrice() {
		return singlePrice;
	}

	public String getUnit() {
		return unit;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public Integer getWarningQuantity() {
		return warningQuantity;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

	public void setBatchNum(String batchNum) {
		this.batchNum = batchNum;
	}

	public void setProductDate(String productDate) {
		this.productDate = productDate;
	}

	public void setStorageTime(String storageTime) {
		this.storageTime = storageTime;
	}

	public void setPurchasePrice(Float purchasePrice) {
		this.purchasePrice = purchasePrice;
	}

	public void setSinglePrice(Float singlePrice) {
		this.singlePrice = singlePrice;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public void setWarningQuantity(Integer warningQuantity) {
		this.warningQuantity = warningQuantity;
	}

}
