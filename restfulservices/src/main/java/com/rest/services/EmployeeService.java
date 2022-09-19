package com.rest.services;

import java.util.List;

import com.rest.services.model.EmployeeDTO;

public interface EmployeeService {
	List<EmployeeDTO> findAllEmployees();
	
	EmployeeDTO findEmployeeById(Integer id);
	
	Integer saveOrUpdate(EmployeeDTO employee);
	
	void delete(Integer id);
	
}
