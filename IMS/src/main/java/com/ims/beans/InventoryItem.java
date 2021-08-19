package com.ims.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="InventoryItem")
public class InventoryItem {
	
	@Id
	@Column(name="inventoryitem_id")
	@SequenceGenerator(name="inventoryitem_id_seq", sequenceName="inventoryitem_id_seq")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="inventoryitem_id_seq")
	private int id;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="DEPARTMENT_ID")
	private Department department;
	
	@Column(name="unit_price",nullable=false)
	private float unitPrice;
	
	@Column(name="quantity",nullable=false)
	private int quantity;
	
	@Column(name="name",nullable=false)
	private String name;
	
	@Column(name="description")
	private String description;
	
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="discount_id")
	private Discount discount;
	
	@Column(name="image")
	private String image;
	
	public InventoryItem() {}

	public InventoryItem(int id, Department department, float unitPrice, int quantity, String name, String description,
			String image) {
		super();
		this.id = id;
		this.department = department;
		this.unitPrice = unitPrice;
		this.quantity = quantity;
		this.name = name;
		this.description = description;
		this.image = image;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public float getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(float unitPrice) {
		this.unitPrice = unitPrice;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Discount getDiscount() {
		return discount;
	}

	public void setDiscount(Discount discount) {
		this.discount = discount;
	}

	@Override
	public String toString() {
		return "InventoryItem [id=" + id + ", department=" + department + ", unitPrice=" + unitPrice + ", quantity="
				+ quantity + ", name=" + name + ", description=" + description + ", image=" + image + "]";
	}

	
}