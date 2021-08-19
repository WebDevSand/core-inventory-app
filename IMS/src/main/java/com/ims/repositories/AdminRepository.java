package com.ims.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ims.beans.Admin;
import com.ims.beans.Customer;

public interface AdminRepository extends JpaRepository<Admin, Integer>{

	List<Admin> findByEmailAndPassword(String email, String password);
}
