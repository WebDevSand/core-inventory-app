package com.ims.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="State")
public class State {
	
	@Id
	@Column(name="STATE_ID")
	private int id;

	@Column
	private String name;

	public State(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public State() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "State [id=" + id + ", name=" + name + "]";
	}
	
	
}
