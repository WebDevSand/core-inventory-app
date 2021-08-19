package com.ims.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ims.beans.LineItem;
import com.ims.beans.Order;

public interface LineItemRepository extends JpaRepository<LineItem, Integer>{

	List<LineItem> findByOrder(Order o);
	List<LineItem> findByOrderId(int id);
	
	@Query(value = "select to_char(t2.order_date, 'MON DD, YYYY') as DateSold, " +
			"sum(t1.QUANTITY) as ItemsSold " + 
			"from LINEITEM t1, ORDERS t2 " + 
			"where t1.order_id = t2.order_id " + 
			"group by t2.ORDER_DATE", nativeQuery=true)
	List<Object> findBySoldByDate();
	
	@Query(value = "select t2.DEPARTMENT_NAME, sum(t1.QUANTITY) as ItemsSold " + 
			"from LINEITEM t1, DEPARTMENT t2, INVENTORYITEM t3 " + 
			"where t1.INVENTORYITEM_ID = t3.INVENTORYITEM_ID " + 
			"and t2.DEPARTMENT_ID = t3.DEPARTMENT_ID " + 
			"group by t2.DEPARTMENT_NAME", nativeQuery=true)
	List<Object> findBySoldByDept();

}
