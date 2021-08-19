package com.ims.beans;

import java.sql.Timestamp;

import javax.persistence.*;


@Entity
@Table(name="Discount")
public class Discount {

	@Id
	@Column(name="Discount_ID")
	@SequenceGenerator(name = "DIS_SEQ", sequenceName = "DIS_SEQ")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DIS_SEQ")
	private int discountID;
	
	@Column(name = "DISCOUNT_TYPE",nullable=false)
	private int discount_Type;
	@Column(name = "AMOUNT",nullable=false)
	private float amount;
	@Column(name = "DESCRIPTION")
	private String description;
	@Column(name = "START_DATE",nullable=false)
	private Timestamp startDate;
	@Column(name = "END_DATE")
	private Timestamp endDate;
	
	
	public Discount() {}
	
	public Discount(int discountID, int discountType, float amount, String description,
			Timestamp startDate, Timestamp endDate) {
		super();
		this.discountID = discountID;
		this.discount_Type = discountType;
		this.amount = amount;
		this.description = description;
		this.startDate = startDate;
		this.endDate = endDate;
	}


	public int getDiscountID() {
		return discountID;
	}


	public void setDiscountID(int discountID) {
		this.discountID = discountID;
	}
	
	public double getDiscount_Type() {
		return discount_Type;
	}
	public void setDiscount_Type(int discountType) {
		this.discount_Type = discountType;
	}


	public float getAmount() {
		return amount;
	}


	public void setAmount(float amount) {
		this.amount = amount;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public Timestamp getStartDate() {
		return startDate;
	}


	public void setStartDate(Timestamp startDate) {
		this.startDate = startDate;
	}


	public Timestamp getEndDate() {
		return endDate;
	}


	public void setEndDate(Timestamp endDate) {
		this.endDate = endDate;
	}


	@Override
	public String toString() {
		return "Discount [discountID=" + discountID + ", discountType="
				+ discount_Type + ", amount=" + amount + ", description=" + description + ", startDate=" + startDate
				+ ", endDate=" + endDate + "]";
	}

	
}