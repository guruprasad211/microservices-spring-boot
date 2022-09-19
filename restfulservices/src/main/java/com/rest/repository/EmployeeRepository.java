package com.rest.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.rest.services.entities.Employee;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Integer>{

}
