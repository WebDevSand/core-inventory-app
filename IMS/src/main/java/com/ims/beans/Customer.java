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
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.ims.beans.Card;
import com.ims.beans.State;

@Entity
@Table(name="Customer")
public class Customer {

	@Id
	@Column(name="CUSTOMER_ID")
	@SequenceGenerator(name = "CUSTOMER_ID_SEQ", sequenceName="CUSTOMER_ID_SEQ")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CUSTOMER_ID_SEQ")
	private int id;
	
	@Column(nullable=false)
	private String firstname;
	@Column(nullable=false)
	private String lastname;
	
	@Column(nullable=false,unique=true)
	private String email;
	@Column(nullable=false)
	private String password;
	
	@Column(nullable=false)
	private String address;

	@Column(nullable=false)
	private String city;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="STATE_ID")
	private State state;
	
	@Column(nullable=false)
	private String zipcode;
	
	@Column
	private String phone;

	@OneToOne(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	@JoinColumn(name="CARD_ID")
	private Card card;

	public Customer() {
	}

	public Customer(int id, String firstname, String lastname, String email, String password, String address,
			String city, State state, String zipcode, String phone) {
		super();
		this.id = id;
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		this.password = password;
		this.address = address;
		this.city = city;
		this.state = state;
		this.zipcode = zipcode;
		this.phone = phone;
	}

	public Customer(int id, String firstname, String lastname, String email, String password, String address,
			String city, State state, String zipcode, String phone, Card card) {
		super();
		this.id = id;
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		this.password = password;
		this.address = address;
		this.city = city;
		this.state = state;
		this.zipcode = zipcode;
		this.phone = phone;
		this.card = card;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Card getCard() {
		return card;
	}

	public void setCard(Card card) {
		this.card = card;
	}

	@Override
	public String toString() {
		return "Customer [id=" + id + ", firstname=" + firstname + ", lastname=" + lastname + ", email=" + email
				+ ", password=" + password + ", address=" + address + ", city=" + city + ", state=" + state.toString()
				+ ", zipcode=" + zipcode + ", phone=" + phone + "]";
	}

	
	
}
