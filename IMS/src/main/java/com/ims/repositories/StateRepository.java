package com.ims.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ims.beans.State;


public interface StateRepository extends JpaRepository<State, Integer> {

}
