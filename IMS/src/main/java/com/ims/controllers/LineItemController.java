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

import com.ims.beans.InventoryItem;
import com.ims.beans.LineItem;
import com.ims.beans.Order;
import com.ims.dtos.LineItemDto;
import com.ims.services.CustomerService;
import com.ims.services.InventoryItemService;
import com.ims.services.LineItemService;
import com.ims.services.OrderService;

@RestController
@RequestMapping("/lineitem")
public class LineItemController {

	@Autowired
	LineItemService liservice;
	
	@Autowired
	InventoryItemService iiservice;
	
	@Autowired
	OrderService oservice;
	
	@Autowired
	CustomerService cservice;
	
	public void setLiService(LineItemService liservice) {
		this.liservice = liservice;
	}
	public void setIiservice(InventoryItemService iiservice) {
		this.iiservice = iiservice;
	}
	public void setOservice(OrderService oservice) {
		this.oservice = oservice;
	}
	public void setCservice(CustomerService cservice) {
		this.cservice = cservice;
	}
	@RequestMapping(value="/create",method=(RequestMethod.POST),
			consumes=(MediaType.APPLICATION_JSON_VALUE),
			produces=(MediaType.APPLICATION_JSON_VALUE))
	public ResponseEntity<LineItemDto> createLineItem(@RequestBody LineItemDto dto){
		System.out.println(dto.toString());
		//get inventory and order objects from id's givenin dto
		InventoryItem a = iiservice.getById(dto.getInventoryitemid());
		Order o = oservice.getOrder(dto.getOrderid());
		
		//setup line item to insert and execute
		LineItem i = new LineItem(dto.getId(),o,dto.getQuantity(),a);
		dto = liservice.createOrUpdateLineItem(i);
		a.setQuantity(a.getQuantity()-dto.getQuantity());
		iiservice.createOrUpdate(a);
		return new ResponseEntity<LineItemDto>(dto, HttpStatus.OK);
		
	}
	
	@RequestMapping(value="/update",method=(RequestMethod.POST),
			consumes=(MediaType.APPLICATION_JSON_VALUE),
			produces=(MediaType.APPLICATION_JSON_VALUE))
	public ResponseEntity<LineItemDto> updateLineItem(@RequestBody LineItemDto dto){
		
		//get inventory and order objects from id's givenin dto
		InventoryItem a = iiservice.getById(dto.getInventoryitemid());
		Order o = oservice.getOrder(dto.getOrderid());
		
		//setup line item to insert and execute
		LineItem i = new LineItem(dto.getId(),o,dto.getQuantity(),a);
		dto = liservice.createOrUpdateLineItem(i);
		return new ResponseEntity<LineItemDto>(dto, HttpStatus.OK);
		
	}
	
	@RequestMapping(value="/getAll",method=(RequestMethod.GET),
			produces=(MediaType.APPLICATION_JSON_VALUE))
	public ResponseEntity<List<LineItemDto>> getAllLineItems(){
		System.out.println("Listing orders: ");
		return new ResponseEntity<List<LineItemDto>>(liservice.getAllLineItems(), HttpStatus.OK);
		
	}
	
	@RequestMapping(value="/getAllByOrder",method=(RequestMethod.GET),
			produces=(MediaType.APPLICATION_JSON_VALUE))
	public ResponseEntity<List<LineItem>> getLineItemsByOrder(Order o){
		//Order o = oservice.getOrder(orderId);
		return new ResponseEntity<List<LineItem>>(liservice.getAllLineItemsByOrder(o), HttpStatus.OK);
	}
	
	@RequestMapping(value="/getAllByOrderId",method=(RequestMethod.GET),
			produces=(MediaType.APPLICATION_JSON_VALUE))
	public ResponseEntity<List<LineItem>> getLineItemsByOrderId(int id){
		//Order o = oservice.getOrder(orderId);
		return new ResponseEntity<List<LineItem>>(liservice.getAllLineItemsByOrderId(id), HttpStatus.OK);
	}

	@RequestMapping(value="/getLine",method=(RequestMethod.GET),
			produces=(MediaType.APPLICATION_JSON_VALUE))
	public ResponseEntity<LineItemDto> getLineId(int id){
		return new ResponseEntity<LineItemDto>(liservice.getLineItemDto(id), HttpStatus.OK);
	}

	@RequestMapping(value="/delete",method=(RequestMethod.POST),
			consumes=(MediaType.APPLICATION_JSON_VALUE),
			produces=(MediaType.APPLICATION_JSON_VALUE))
	public ResponseEntity<String> removeLineItem(@RequestBody LineItemDto dto){
		LineItem i = liservice.getLineItem(dto.getId());
		liservice.deleteLineItem(i);
		return new ResponseEntity<String>("true", HttpStatus.OK);
		
	}
	
	@RequestMapping("/soldByDate")
	public List<Object> findBySoldByDate() {
		return liservice.findBySoldByDate();
	}

	@RequestMapping("/soldByDept")
	public List<Object> findBySoldByDept() {
		return liservice.findBySoldByDept();
	}

}
