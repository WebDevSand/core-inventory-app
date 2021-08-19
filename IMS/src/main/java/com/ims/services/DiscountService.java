package com.ims.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ims.beans.Discount;
import com.ims.daos.DiscountDao;

@Service
public class DiscountService {

	@Autowired
	private DiscountDao discountDao;

	public void setDiscountDao(DiscountDao discountDao) {
		this.discountDao = discountDao;
	}

	public Discount createOrUpdate(Discount i) {
		return discountDao.createOrUpdateDiscount(i);
	}

	public Discount getById(int id) {
		return discountDao.getDiscountById(id);
	}
	
	public List<Discount> getAll() {
		return discountDao.getAll();
	}

	public void remove(Discount i) {
		discountDao.removeDiscount(i);
	}

	public Discount getDiscount(int id) {
		return discountDao.getDiscountById(id);
	}
	
}
