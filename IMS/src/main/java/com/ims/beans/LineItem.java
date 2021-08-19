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
@Table(name = "LINEITEM")
public class LineItem {

	@Id
	@Column(name = "LINEITEM_ID")
	@SequenceGenerator(name = "LINEITEM_ID_SEQ",sequenceName = "LINEITEM_ID_SEQ")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "LINEITEM_ID_SEQ")
	private int id;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="ORDER_ID",nullable=false)
	private Order order;
	
	@Column(nullable=false)
	private int quantity;
	
	@OneToOne
	@JoinColumn(name = "inventoryitem_id",nullable=false)
	private InventoryItem inventoryItem;

	public LineItem() {
		
	}
	
	
	public LineItem(int id, Order order, int quantity, InventoryItem item) {
		super();
		this.id = id;
		this.order = order;
		this.quantity = quantity;
		this.inventoryItem = item;
	}


	
	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public InventoryItem getInventoryItem() {
		return inventoryItem;
	}


	public void setInventoryItem(InventoryItem item) {
		this.inventoryItem = item;
	}


	@Override
	public String toString() {
		return "LineItem [id=" + id + ", order=" + order.toString() + ", quantity=" + quantity + ", item=" + inventoryItem.toString()
				+ "]";
	}


	
	
	
	
}