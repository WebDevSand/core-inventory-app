package com.ims.daos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ims.beans.Department;
import com.ims.repositories.DepartmentRepository;

@Component
@Transactional
public class DepartmentDao {
	
	@Autowired
	private DepartmentRepository aRepo;
	
	public DepartmentDao() {
		
	}
	
	public void setDepartmentRepo(DepartmentRepository aRepo) {
		this.aRepo = aRepo;
	}
	
	public Department createOrUpdateDepartment(Department s) {
		return aRepo.save(s);
	}
	public List<Department> getAll(){
		return (List<Department>) aRepo.findAll();
	}
	public Department getDepartmentById(int id) {
		return aRepo.findOne(id);
	}
	public Department getDepartmentByName(String name) {
		List<Department> result = aRepo.findByName(name);
		if(result.size() == 0) {
			return null;
		}
		else {
			return result.get(0);
		}
	}
	public void removeDepartment(Department s) {
		aRepo.delete(s);
	}

}
