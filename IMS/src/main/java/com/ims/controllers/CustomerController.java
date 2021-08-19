package com.ims.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ims.beans.Customer;
import com.ims.dtos.CustomerDto;
import com.ims.services.CustomerService;

@RestController
@RequestMapping(value="/customer")
public class CustomerController {

	@Autowired
	private CustomerService customerService;

	public void setInventoryItemService(CustomerService customerService) {
		this.customerService = customerService;
	}
	
	@RequestMapping(value="/auth",method=(RequestMethod.POST),
			consumes=(MediaType.APPLICATION_JSON_VALUE),
			produces=(MediaType.APPLICATION_JSON_VALUE))
	public ResponseEntity<CustomerDto> authenticateUser(@RequestBody CustomerDto customerDto){
		System.out.println("Authenticating user: " + customerDto.getEmail());
		return new ResponseEntity<CustomerDto>(customerService.authenticateUser(customerDto), HttpStatus.OK);
	}
	
	@RequestMapping(value="/create",method=(RequestMethod.POST),
			consumes=(MediaType.APPLICATION_JSON_VALUE),
			produces=(MediaType.APPLICATION_JSON_VALUE))
	public ResponseEntity<CustomerDto> addCustomer(@RequestBody CustomerDto c){
		return new ResponseEntity<CustomerDto>(customerService.createOrUpdate(c), HttpStatus.OK);
	}
	

	@RequestMapping(value="/getAll",method=(RequestMethod.GET),produces=(MediaType.APPLICATION_JSON_VALUE))
	public ResponseEntity<List<Customer>> getAllCustomers(){
		return new ResponseEntity<List<Customer>>(customerService.getAll(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/update",method=(RequestMethod.POST),
			consumes=(MediaType.APPLICATION_JSON_VALUE),
			produces=(MediaType.APPLICATION_JSON_VALUE))
	public ResponseEntity<CustomerDto> updateItem(@RequestBody CustomerDto c){
		System.out.println("Updating: " + c.toString());
		
		return new ResponseEntity<CustomerDto>(customerService.createOrUpdate(c), HttpStatus.OK);
	}
	
	@RequestMapping(value="/delete",method=(RequestMethod.POST),
			consumes=(MediaType.APPLICATION_JSON_VALUE),
			produces=(MediaType.APPLICATION_JSON_VALUE))
	public ResponseEntity<String> removeItem(@RequestBody CustomerDto c){
		Customer cust = new Customer(c.getId(),c.getFirstname(),c.getLastname(),c.getEmail(),c.getPassword(),
				c.getAddress(),c.getCity(),c.getState(),c.getZipcode(),c.getPhone(),
				c.getCard());
		customerService.remove(cust);
		return new ResponseEntity<String>("true", HttpStatus.OK);
	}
}
