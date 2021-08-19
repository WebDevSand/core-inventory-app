package com.ims.repositories;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ims.beans.Discount;

public interface DiscountRepository extends JpaRepository<Discount, Integer>{
	
	List<Discount> findByStartDate(Timestamp date);
	
	List<Discount> findByEndDate(Timestamp date);
}
