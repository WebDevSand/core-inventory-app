package com.ims.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ims.beans.Card;
import com.ims.daos.CardDao;

@Service

@Transactional
public class CardService {

	@Autowired
	private CardDao cDao;

	public void setcDao(CardDao cDao) {
		this.cDao = cDao;
	}
	
	public Card createCard(Card c) {
		return cDao.addCard(c);
	}
	
	public void deleteCard(Card c) {
		cDao.removeCard(c);
	}
}
