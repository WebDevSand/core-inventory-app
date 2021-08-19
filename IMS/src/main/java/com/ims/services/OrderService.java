package com.ims.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ims.beans.Order;
import com.ims.daos.OrderDao;
import com.ims.dtos.OrderDto;
import java.sql.*;
import java.util.List;

@Service
public class OrderService {

	@Autowired
	OrderDao dao;

	public void setDao(OrderDao dao) {
		this.dao = dao;
	}
	
	public Order createOrUpdateOrder(Order o) {
		return dao.addOrder(o);
	}
	
	
	public List<Order> getAllOrders(){
		return dao.getAll();
	}
	
	public List<Order> getAllOrdersFromCustomerId(int id){
		return dao.getAllFromCustomerId(id);
	}
	public Order getOrder(int id){
		return dao.getOrderById(id);
	}
	
	public void deleteOrder(Order o) {
		dao.removeOrder(o);
	}
	
	
	
	
}
