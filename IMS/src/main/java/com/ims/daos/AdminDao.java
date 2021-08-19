package com.ims.daos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ims.beans.Admin;
import com.ims.repositories.AdminRepository;

@Component
@Transactional
public class AdminDao{

	@Autowired
	private AdminRepository adminRepository;
	
	public AdminDao() {
		
	}
	
	public void setAdminRepository(AdminRepository adminRepository) {
		this.adminRepository = adminRepository;
	}
	
	public Admin saveAdmin(Admin s) {
		return adminRepository.save(s);
	}
	public List<Admin> getAll(){
		return (List<Admin>) adminRepository.findAll();
	}
	public Admin getAdminById(int id) {
		return adminRepository.findOne(id);
	}
	public Admin getAdminByUsernameAndPassword(String email,String password) {
		List<Admin> result = adminRepository.findByEmailAndPassword(email, password);
		if(result.size() == 0) {
			return null;
		}
		else {
			return result.get(0);
		}
	}
	public void removeAdmin(Admin s) {
		adminRepository.delete(s);;
	}
}
