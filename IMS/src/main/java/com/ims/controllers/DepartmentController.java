package com.ims.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ims.beans.Department;
import com.ims.beans.Order;
import com.ims.services.DepartmentService;

@RestController
@RequestMapping(value="/department")
public class DepartmentController {

	@Autowired
	private DepartmentService departmentService;
	
	public void setDepartmentService(DepartmentService departmentService) {
		this.departmentService = departmentService;
	}
	
	@RequestMapping(value="/create",method=(RequestMethod.POST),
			consumes=(MediaType.APPLICATION_JSON_VALUE),
			produces=(MediaType.APPLICATION_JSON_VALUE))
	public ResponseEntity<Department> addDepartment(@RequestBody Department d){
		return new ResponseEntity<Department>(departmentService.createOrUpdate(d), HttpStatus.OK);
	}
	
	@RequestMapping(value="/getAll",method=(RequestMethod.GET),produces=(MediaType.APPLICATION_JSON_VALUE))
	public ResponseEntity<List<Department>> getAllDepartments(){
		return new ResponseEntity<List<Department>>(departmentService.getAll(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/update",method=(RequestMethod.POST),
			consumes=(MediaType.APPLICATION_JSON_VALUE),
			produces=(MediaType.APPLICATION_JSON_VALUE))
	public ResponseEntity<Department> updateDepartment(@RequestBody Department d){
		return new ResponseEntity<Department>(departmentService.createOrUpdate(d), HttpStatus.OK);
	}
	
	@RequestMapping(value="/get",method=(RequestMethod.GET),
			produces=(MediaType.APPLICATION_JSON_VALUE))
	public ResponseEntity<Department> getDepartment(int id){
		return new ResponseEntity<Department>(departmentService.getDepartment(id), HttpStatus.OK);
		
	}
	
	@RequestMapping(value="/remove",method=(RequestMethod.POST),
			consumes=(MediaType.APPLICATION_JSON_VALUE),
			produces=(MediaType.APPLICATION_JSON_VALUE))
	public ResponseEntity<String> removeDepartment(@RequestBody Department d){
		departmentService.remove(d);
		return new ResponseEntity<String>("true", HttpStatus.OK);
	}
}
