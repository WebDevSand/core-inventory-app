package com.ims.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ims.beans.State;
import com.ims.services.StateService;

@RestController
@RequestMapping(value="/state")
public class StateController {

	@Autowired
	private StateService stateService;
	
	public void setStateServiceImpl(StateService stateService) {
		this.stateService = stateService;
	}

	@RequestMapping(value="/getAll",method=(RequestMethod.GET),
			produces=(MediaType.APPLICATION_JSON_VALUE))
	public ResponseEntity<List<State>> getAllStates(){
		return new ResponseEntity<List<State>>(stateService.getAll(), HttpStatus.OK);
	}

}
