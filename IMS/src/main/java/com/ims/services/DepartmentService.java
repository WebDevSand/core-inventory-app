package com.ims.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ims.beans.Department;
import com.ims.beans.Order;
import com.ims.daos.DepartmentDao;

@Service
public class DepartmentService {

	@Autowired
	private DepartmentDao departmentDao;

	public void setDepartmentDao(DepartmentDao departmentDao) {
		this.departmentDao = departmentDao;
	}

	public Department createOrUpdate(Department d) {
		return departmentDao.createOrUpdateDepartment(d);
	}

	public List<Department> getAll(){
		return departmentDao.getAll();
	}

	public void remove(Department d) {
		departmentDao.removeDepartment(d);
	}
	
	public Department getById(int id) {
		return departmentDao.getDepartmentById(id);
	}

	public Department getDepartment(int id) {
		return departmentDao.getDepartmentById(id);
	}
	

}
