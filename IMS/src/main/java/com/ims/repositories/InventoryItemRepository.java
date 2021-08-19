package com.ims.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ims.beans.Department;
import com.ims.beans.Discount;
import com.ims.beans.InventoryItem;

public interface InventoryItemRepository extends JpaRepository<InventoryItem, Integer> {

	List<InventoryItem> findByDepartment(Department d);
	
	List<InventoryItem> findByDepartmentId(int d);
	
	List<InventoryItem> findByDiscount(Discount d);
	
	@Query(value = "select t2.department_name as dept, count(*) as qty "
			+ "from InventoryItem t1, Department t2 "
			+ "where t1.department_id = t2.department_id "
			+ "group by t2.department_name", nativeQuery=true)
	List<Object> findByItemsByDept();
	
	@Query(value = "select t3.department_name, count(*) as Count " + 
			"from discount t1, inventoryitem t2, department t3 " + 
			"where t1.discount_id = t2.DISCOUNT_ID " + 
			"and t2.DEPARTMENT_ID = t3.DEPARTMENT_ID " + 
			"group by t3.department_name", nativeQuery=true)
	List<Object> findByDiscountedItemsByDept();
}