package com.ims.daos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ims.beans.LineItem;
import com.ims.beans.Order;
import com.ims.repositories.LineItemRepository;



@Component
@Transactional
public class LineItemDao {

	@Autowired
	private LineItemRepository aRepo;
	
	public LineItemDao() {
		
	}
	
	public void setLineItemRepo(LineItemRepository aRepo) {
		this.aRepo = aRepo;
	}
	
	public LineItem addLineItem(LineItem s) {
		return aRepo.save(s);
	}
	public List<LineItem> getAll(){
		return (List<LineItem>) aRepo.findAll();
	}
	
	public List<LineItem> getAllByOrder(Order o){
		return aRepo.findByOrder(o);
	}
	public List<LineItem> getAllByOrderId(int id){
		return aRepo.findByOrderId(id);
	}
	public LineItem getLineItemById(int id) {
		return aRepo.findOne(id);
	}
	public void updateLineItem(LineItem s) {
		aRepo.saveAndFlush(s);
	}
	public void removeLineItem(LineItem s) {
		aRepo.delete(s);
	}
}
