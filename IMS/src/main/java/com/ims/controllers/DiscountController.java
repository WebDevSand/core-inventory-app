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

import com.ims.beans.Discount;
import com.ims.beans.Order;
import com.ims.services.DiscountService;

@RestController
@RequestMapping(value="/discount")
public class DiscountController {

	@Autowired
	private DiscountService discountService;
	
	public void setDiscountService(DiscountService discountService) {
		this.discountService = discountService;
	}
	
	
	@RequestMapping(value="/create",method=(RequestMethod.POST),
			consumes=(MediaType.APPLICATION_JSON_VALUE),
			produces=(MediaType.APPLICATION_JSON_VALUE))
	public ResponseEntity<Discount> createOrUpdate(@RequestBody Discount d){
		//return and create discount
		return new ResponseEntity<Discount>(discountService.createOrUpdate(d), HttpStatus.OK);
	}
	
	@RequestMapping(value="/update",method=(RequestMethod.POST),
			consumes=(MediaType.APPLICATION_JSON_VALUE),
			produces=(MediaType.APPLICATION_JSON_VALUE))
	public ResponseEntity<Discount> update(@RequestBody Discount d){
		//return and create discount
		return new ResponseEntity<Discount>(discountService.createOrUpdate(d), HttpStatus.OK);
	}
	
	@RequestMapping(value="/getAll",method=(RequestMethod.GET),produces=(MediaType.APPLICATION_JSON_VALUE))
	public ResponseEntity<List<Discount>> getAllDepartments(){
		return new ResponseEntity<List<Discount>>(discountService.getAll(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/get",method=(RequestMethod.GET),
			produces=(MediaType.APPLICATION_JSON_VALUE))
	public ResponseEntity<Discount> getDiscount(int id){
		return new ResponseEntity<Discount>(discountService.getDiscount(id), HttpStatus.OK);
		
	}
	
	@RequestMapping(value="/remove",method=(RequestMethod.POST),
			consumes=(MediaType.APPLICATION_JSON_VALUE),
			produces=(MediaType.APPLICATION_JSON_VALUE))
	public ResponseEntity<String> removeDepartment(@RequestBody Discount d){
		//get inventory item, set discount to null, save
		
		discountService.remove(d);
		return new ResponseEntity<String>("true", HttpStatus.OK);
	}
}
