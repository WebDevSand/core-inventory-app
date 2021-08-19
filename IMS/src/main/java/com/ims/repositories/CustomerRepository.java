package com.ims.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ims.beans.Card;
import com.ims.beans.Customer;
import com.ims.beans.State;

public interface CustomerRepository extends JpaRepository<Customer, Integer>{

	List<Customer> findByEmailAndPassword(String email, String password);
	List<Customer> findBylastname(String lastname);
	
	List<Customer> findByState(State state);
	
	Customer findByCard(Card card);
	
	Customer findByCardId(int cardid);
}
