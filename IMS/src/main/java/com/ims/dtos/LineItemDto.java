package com.ims.dtos;

public class LineItemDto {
	
	private int id;
	private int orderid;
	private int quantity;
	private int inventoryitemid;
	
	public LineItemDto(int id, int orderid, int quantity, int inventoryitemid) {
		super();
		this.id = id;
		this.orderid = orderid;
		this.quantity = quantity;
		this.inventoryitemid = inventoryitemid;
	}
	public LineItemDto() {
		super();
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getOrderid() {
		return orderid;
	}
	public void setOrderid(int orderid) {
		this.orderid = orderid;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public int getInventoryitemid() {
		return inventoryitemid;
	}
	public void setInventoryitemid(int inventoryitemid) {
		this.inventoryitemid = inventoryitemid;
	}
	
	
}
