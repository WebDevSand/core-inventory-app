package com.ims.beans;

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
@Table(name="ProductReview")
public class ProductReview {

	@Id
	@Column(name="review_id")
	@SequenceGenerator(name="review_id_seq", sequenceName="review_id_seq")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="review_id_seq")
	private int id;
	
	@ManyToOne(fetch=FetchType.EAGER,cascade=CascadeType.ALL)
	@JoinColumn(name="inventoryitem_id")
	private InventoryItem inventoryItem;
	
	@Column(name="rating",nullable=false)
	private int rating;
	
	@Column(name="description")
	private String description;

	public ProductReview() {}

	public ProductReview(int id, InventoryItem inventoryItem, int rating, String description) {
		super();
		this.id = id;
		this.inventoryItem = inventoryItem;
		this.rating = rating;
		this.description = description;
	}

	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public InventoryItem getInventoryItem() {
		return inventoryItem;
	}

	public void setInventoryItem(InventoryItem inventoryItem) {
		this.inventoryItem = inventoryItem;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "ProductReview [id=" + id + ", inventoryItem=" + inventoryItem.toString() + ", rating=" + rating + ", comment="
				+ description + "]";
	}
	
	
}
