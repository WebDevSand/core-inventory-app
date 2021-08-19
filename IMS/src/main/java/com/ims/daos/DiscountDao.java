package com.ims.daos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ims.beans.Discount;
import com.ims.repositories.DiscountRepository;

@Component
@Transactional
public class DiscountDao {
	
	@Autowired
	private DiscountRepository aRepo; //i spelled it wrong...
	
	public DiscountDao() {
		
	}
	
	public void setDiscountRepo(DiscountRepository aRepo) {
		this.aRepo = aRepo;
	}
	
	public Discount createOrUpdateDiscount(Discount s) {
		return aRepo.save(s);
	}
	public List<Discount> getAll(){
		return (List<Discount>) aRepo.findAll();
	}
	public Discount getDiscountById(int id) {
		return aRepo.findOne(id);
	}
	public void updateDiscount(Discount s) {
		aRepo.saveAndFlush(s);
	}
	public void removeDiscount(Discount s) {
		aRepo.delete(s);
	}

}
