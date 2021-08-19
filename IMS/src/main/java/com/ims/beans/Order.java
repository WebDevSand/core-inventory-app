package com.ims.beans;


import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="Orders")
public class Order {
    

    @Id
    @Column(name="ORDER_ID")
    @SequenceGenerator(name="ORDERID_SEQ", sequenceName="ORDERID_SEQ")
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ORDERID_SEQ")
    private int id;
    
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="CUSTOMER_ID",nullable=false)
    private Customer customer;
    
    @Column(nullable = false)
    private Timestamp order_Date;
    
    public Order() {
        super();
    }
    
    public Order(int id, Customer customer, Timestamp order_Date) {
		super();
		this.id = id;
		this.customer = customer;
		this.order_Date = order_Date;
	}

	public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    
    public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public Timestamp getOrder_Date() {
        return order_Date;
    }
    public void setOrder_Date(Timestamp order_Date) {
        this.order_Date = order_Date;
    }
	@Override
	public String toString() {
		return "Order [id=" + id + ", customer=" + customer.toString() +", order_Date=" + order_Date
				+ "]";
	}
    
    
}