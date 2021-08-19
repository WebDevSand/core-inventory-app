package com.ims.dtos;

import java.sql.Timestamp;

public class OrderDto {
	
	private int id;
	private int customerid;
	private Timestamp orderdate;
	
	public OrderDto(int id, int customerid, Timestamp orderdate) {
		super();
		this.id = id;
		this.customerid = customerid;
		this.orderdate = orderdate;
	}
	public OrderDto() {
		super();
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getCustomerid() {
		return customerid;
	}
	public void setCustomerid(int customerid) {
		this.customerid = customerid;
	}
	public Timestamp getOrderdate() {
		return orderdate;
	}
	public void setOrderdate(Timestamp orderdate) {
		this.orderdate = orderdate;
	}
	
	
}
