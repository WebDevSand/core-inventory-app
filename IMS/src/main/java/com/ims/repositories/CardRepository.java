package com.ims.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ims.beans.Card;

public interface CardRepository extends JpaRepository<Card, Integer>{

}
