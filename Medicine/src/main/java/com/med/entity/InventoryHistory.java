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
@Table(name = "tbl_inventory_history")
public class InventoryHistory {

	@Id
	@Column(name = "ih_id")
	@Basic(fetch = FetchType.EAGER)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "ih_med_name")
	@Basic(fetch = FetchType.LAZY)
	private String medName;
	
	@Column(name = "ih_quantity")
	@Basic(fetch = FetchType.LAZY)
	private Integer quantity;
	
	@Column(name = "ih_time")
	@Basic(fetch = FetchType.LAZY)
	private String time;
	
	@Column(name = "ih_optype")
	@Basic(fetch = FetchType.LAZY)
	private Boolean optype;

	public Integer getId() {
		return id;
	}

	public String getMedName() {
		return medName;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public String getTime() {
		return time;
	}

	public Boolean getOptype() {
		return optype;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setMedName(String medName) {
		this.medName = medName;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public void setOptype(Boolean optype) {
		this.optype = optype;
	}
}
