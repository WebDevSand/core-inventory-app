package com.ims.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ims.beans.Card;
import com.ims.services.CardService;

@RestController
@RequestMapping(value = "/card")
public class CardController {
	
	@Autowired
	private CardService cardService;
	
	public void setUserServiceImpl(CardService cardService) {
		this.cardService = cardService;
	}
	
	
	@RequestMapping(value="/create",method=(RequestMethod.POST),
			consumes=(MediaType.APPLICATION_JSON_VALUE),
			produces=(MediaType.APPLICATION_JSON_VALUE))
	public ResponseEntity<Card> createCard(@RequestBody Card c){
		System.out.println("Creating card: " + c.getNameoncard());
		return new ResponseEntity<Card>(cardService.createCard(c), HttpStatus.OK);
		
	}
	
	
	@RequestMapping(value="/update",method=(RequestMethod.POST),
			consumes=(MediaType.APPLICATION_JSON_VALUE),
			produces=(MediaType.APPLICATION_JSON_VALUE))
	public ResponseEntity<Card> updateCard(@RequestBody Card c){
		System.out.println("Updating card: " + c.getNameoncard());
		return new ResponseEntity<Card>(cardService.createCard(c), HttpStatus.OK);
		
	}
	
	@RequestMapping(value="/delete",method=(RequestMethod.POST),
			consumes=(MediaType.APPLICATION_JSON_VALUE),
			produces=(MediaType.APPLICATION_JSON_VALUE))
	public ResponseEntity<String> removeOrder(@RequestBody Card c){
		cardService.deleteCard(c);
		return new ResponseEntity<String>("true", HttpStatus.OK);
		
	}
}
