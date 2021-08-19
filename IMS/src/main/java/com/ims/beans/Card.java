package com.ims.beans;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "CARD")
public class Card {

	@Id
	@Column(name="CARD_ID")
	@SequenceGenerator(name = "CARD_ID_SEQ", sequenceName="CARD_ID_SEQ")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CARD_ID_SEQ")
	private int id;
	
	@Column(nullable=false)
	private String cardnumber;
	@Column(nullable=false)
	private String nameoncard;
	@Column(nullable=false)
	private String expiration;
	@Column(nullable=false)
	private String securitycode;
	
	public Card() {
		
	}


	public Card(int id, String cardnumber, String nameoncard, String expiration, String securitycode) {
		super();
		this.id = id;
		this.cardnumber = cardnumber;
		this.nameoncard = nameoncard;
		this.expiration = expiration;
		this.securitycode = securitycode;
	}




	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}

	public String getCardnumber() {
		return cardnumber;
	}
	public void setCardnumber(String cardnumber) {
		this.cardnumber = cardnumber;
	}
	public String getNameoncard() {
		return nameoncard;
	}
	public void setNameoncard(String nameoncard) {
		this.nameoncard = nameoncard;
	}
	public String getExpiration() {
		return expiration;
	}
	public void setExpiration(String expiration) {
		this.expiration = expiration;
	}
	public String getSecuritycode() {
		return securitycode;
	}
	public void setSecuritycode(String securitycode) {
		this.securitycode = securitycode;
	}


	@Override
	public String toString() {
		return "Card [id=" + id  + ", cardnumber=" + cardnumber + ", nameoncard=" + nameoncard
				+ ", expiration=" + expiration + ", securitycode=" + securitycode + "]";
	}



	
	
	
	
	
}
