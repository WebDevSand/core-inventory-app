package com.ims.daos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


import com.ims.beans.ProductReview;
import com.ims.repositories.ProductReviewRepository;

@Component
@Transactional
public class ProductReviewDao {

	@Autowired
	private ProductReviewRepository aRepo;
	
	public ProductReviewDao() {}
	
	public void setProductReviewRepo(ProductReviewRepository aRepo) {
		this.aRepo = aRepo;
	}
	
	public ProductReview saveReview(ProductReview s) {
		return aRepo.save(s);
	}
	public List<ProductReview> getAll(){
		return (List<ProductReview>) aRepo.findAll();
	}
	public ProductReview getReviewById(int id) {
		return aRepo.findOne(id);
	}
	public void removeReview(ProductReview s) {
		aRepo.delete(s);
	}
	public List<ProductReview> getAllByInventoryItemId(int id){
		return aRepo.findByInventoryItemId(id);
	}
	
	public float getAverage(int id) {
		return aRepo.averageRatingById(id);
	}

	public List<ProductReview> getReviewByItemId(int id) {
		return aRepo.findByInventoryItemId(id);
	}

}
