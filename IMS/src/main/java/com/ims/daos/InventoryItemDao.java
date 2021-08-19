package com.ims.daos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ims.beans.InventoryItem;
import com.ims.repositories.InventoryItemRepository;

@Component
@Transactional
public class InventoryItemDao {

	@Autowired
	private InventoryItemRepository aRepo;
	
	public InventoryItemDao() {
		
	}
	
	public void setInventoryItemRepo(InventoryItemRepository aRepo) {
		this.aRepo = aRepo;
	}
	
	public InventoryItem createOrUpdateInventoryItem(InventoryItem s) {
		return aRepo.save(s);
	}
	public List<InventoryItem> getAll(){
		return (List<InventoryItem>) aRepo.findAll();
	}
	public InventoryItem getInventoryItemById(int id) {
		return aRepo.findOne(id);
	}
	public void removeInventoryItem(InventoryItem s) {
		aRepo.delete(s);
	}
}