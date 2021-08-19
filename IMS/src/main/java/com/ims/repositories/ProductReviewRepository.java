package com.ims.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ims.beans.InventoryItem;
import com.ims.beans.ProductReview;

public interface ProductReviewRepository extends JpaRepository<ProductReview, Integer> {

	List<ProductReview> findByInventoryItem(InventoryItem ii);
	
	List<ProductReview> findByInventoryItemId(int ii);
	
	List<ProductReview> findByRating(int rating);
	
	@Query("SELECT AVG(pr.rating) FROM ProductReview pr WHERE pr.inventoryItem.id=:id")
	float averageRatingById(@Param("id")int id);
}
