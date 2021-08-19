package com.ims.daos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ims.beans.State;
import com.ims.repositories.StateRepository;

@Component
@Transactional
public class StatesDao {

	@Autowired
	private StateRepository sRepo;
	
	public StatesDao() {
		
	}
	
	public void setStateRepo(StateRepository sRepo) {
		this.sRepo = sRepo;
	}
	
	public void addState(State s) {
		sRepo.save(s);
	}
	public List<State> getAll(){
		return (List<State>) sRepo.findAll();
	}
	public State getStateById(int id) {
		return sRepo.findOne(id);
	}
	public void updateState(State s) {
		sRepo.saveAndFlush(s);
	}
	public void removeState(State s) {
		sRepo.delete(s);
	}
}
