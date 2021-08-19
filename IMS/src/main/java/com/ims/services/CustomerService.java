package com.ims.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ims.beans.Card;
import com.ims.beans.Customer;
import com.ims.beans.State;
import com.ims.daos.CustomerDao;
import com.ims.dtos.CustomerDto;

@Service
public class CustomerService {

	@Autowired
	private CustomerDao customerDao;

	public void setCustomerDao(CustomerDao customerDao) {
		this.customerDao = customerDao;
	}

	public CustomerDto createOrUpdate(CustomerDto c) {
		Customer cust = new Customer(c.getId(),c.getFirstname(),c.getLastname(),c.getEmail(),c.getPassword(),
												c.getAddress(),c.getCity(),c.getState(),c.getZipcode(),c.getPhone(),
												c.getCard());
		cust = customerDao.createOrUpdateCustomer(cust);
		c.setId(cust.getId());
		c.setAuthenticated(true);
		return c;
	}
	
	public CustomerDto authenticateUser(CustomerDto customerDto) {
		Customer c = customerDao.getCustomerByUsernameAndPassword(customerDto.getEmail(),customerDto.getPassword());
		if(c != null) {
			customerDto = new CustomerDto(c.getId(),c.getFirstname(),c.getLastname(),c.getEmail(),c.getPassword(),
												c.getAddress(),c.getCity(),c.getState(),c.getZipcode(),c.getPhone(),
												c.getCard(),true);
		}
		return customerDto;
	}
	
	public List<Customer> getAll() {
		return customerDao.getAll();
	}

	public Customer getById(int i) {
		return customerDao.getCustomerById(i);
	}
	
	public void remove(Customer c) {
		customerDao.removeCustomer(c);
	}
	
	public List<Customer> getAllByLastName(String ln){
		return customerDao.getAllByLastName(ln);
	}
	
	
	public List<Customer> getAllByState(State s){
		return customerDao.getAllByState(s);
	}
	
	public Customer getByCard(Card c) {
		return customerDao.getByCard(c);
	}
	
	public Customer getByCardId(int id) {
		return customerDao.getByCardId(id);
	}
}
