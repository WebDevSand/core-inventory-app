package com.ims.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ims.beans.ProductReview;
import com.ims.daos.ProductReviewDao;
import com.ims.dtos.ProductReviewDto;


@Service
public class ProductReviewService {

	@Autowired
	private ProductReviewDao productReviewDao;

	public void setAdminDao(ProductReviewDao productReviewDao) {
		this.productReviewDao = productReviewDao;
	}
	
	public ProductReview createOrUpdateProductReview(ProductReview productReview) {
		//add to Db
		return productReviewDao.saveReview(productReview);
//		//create dto
//		ProductReviewDto productReviewDto = new ProductReviewDto(productReview.getId(),productReview.getRating(),productReview.getDescription());
//		return productReviewDto;
	}
	
	public void removeReview(ProductReview productReview) {
		// remove from DB
		productReviewDao.removeReview(productReview);
	}
	
	public List<ProductReview> getAll() {
		return productReviewDao.getAll();
	}
	
	public List<ProductReview> getAllFromInventoryItemId(int id){
		return productReviewDao.getAllByInventoryItemId(id);
	}
	
	public float getAverage(int id) {
		return productReviewDao.getAverage(id);
	}

	public List<ProductReview> getByItemId(int id) {
		return productReviewDao.getReviewByItemId(id);
	}

}
