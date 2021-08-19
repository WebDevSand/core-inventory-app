package com.ims.dtos;

public class InventoryItemDto {

	private int id;
	private int departmentid;
	private float unitPrice;
	private int quantity;
	private String name;
	private String description;
	private int discountid;
	private String image;
	public InventoryItemDto() {
		super();
	}
	public InventoryItemDto(int id, int departmentid, float unitPrice, int quantity, String name, String description,
			int discountid, String image) {
		super();
		this.id = id;
		this.departmentid = departmentid;
		this.unitPrice = unitPrice;
		this.quantity = quantity;
		this.name = name;
		this.description = description;
		this.discountid = discountid;
		this.image = image;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getDepartmentid() {
		return departmentid;
	}
	public void setDepartmentid(int departmentid) {
		this.departmentid = departmentid;
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
	public int getDiscountid() {
		return discountid;
	}
	public void setDiscountid(int discountid) {
		this.discountid = discountid;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	@Override
	public String toString() {
		return "InventoryItemDto [id=" + id + ", departmentid=" + departmentid + ", unitPrice=" + unitPrice
				+ ", quantity=" + quantity + ", name=" + name + ", description=" + description + ", discountid="
				+ discountid + ", image=" + image + "]";
	}
	
	
}
