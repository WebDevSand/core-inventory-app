package com.ims.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ims.beans.State;
import com.ims.daos.StatesDao;

@Service
public class StateService {

	@Autowired
	private StatesDao statesDao;

	public void setStatesDao(StatesDao statesDao) {
		this.statesDao = statesDao;
	}

	public List<State> getAll() {
		return statesDao.getAll();
	}

}
