package com.ims.daos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ims.beans.Card;
import com.ims.beans.Customer;
import com.ims.beans.State;
import com.ims.repositories.CustomerRepository;

@Component
@Transactional
public class CustomerDao {
	
	@Autowired
	private CustomerRepository aRepo;
	
	public CustomerDao() {
		
	}
	
	public void setCustomerRepo(CustomerRepository aRepo) {
		this.aRepo = aRepo;
	}
	
	public Customer createOrUpdateCustomer(Customer s) {
		return aRepo.save(s);
	}
	
	public List<Customer> getAll(){
		return (List<Customer>) aRepo.findAll();
	}
	
	public Customer getCustomerByUsernameAndPassword(String email,String password) {
		List<Customer> result = aRepo.findByEmailAndPassword(email, password);
		if(result.size() == 0) {
			return null;
		}
		else {
			return result.get(0);
		}
	}
	public Customer getCustomerById(int id) {
		return aRepo.findOne(id);
	}
	public void updateCustomer(Customer s) {
		aRepo.saveAndFlush(s);
	}
	public void removeCustomer(Customer s) {
		aRepo.delete(s);
	}
	
	public List<Customer> getAllByLastName(String ln){
		return aRepo.findBylastname(ln);
	}
	
	
	public List<Customer> getAllByState(State s){
		return aRepo.findByState(s);
	}
	
	public Customer getByCard(Card c) {
		return aRepo.findByCard(c);
	}
	
	public Customer getByCardId(int id) {
		return aRepo.findByCardId(id);
	}

}
