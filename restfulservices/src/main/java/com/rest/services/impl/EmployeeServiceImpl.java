package com.rest.services.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rest.repository.EmployeeRepository;
import com.rest.services.EmployeeService;
import com.rest.services.entities.Employee;
import com.rest.services.model.EmployeeDTO;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	EmployeeRepository employeeRepository;
	
	@Autowired
    private ModelMapper modelMapper;

	@Override
	public List<EmployeeDTO> findAllEmployees() {
		//List<Employee> employees = new ArrayList<>();
		//employeeRepository.findAll().forEach(employees::add);
		
		//convert to dto emp and add
		List<EmployeeDTO> employeesDTO = new ArrayList<>();
		employeeRepository.findAll().forEach(e -> {
			employeesDTO.add(convertToDto(e));
		});
		return employeesDTO;
	}

	@Override
	public EmployeeDTO findEmployeeById(Integer id) {
		Employee emp = employeeRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Employee not found"));
		return convertToDto(emp);
	}

	@Override
	public Integer saveOrUpdate(EmployeeDTO employee) {
		//to update fewer columns use custom query if more columns exists
		return employeeRepository.save(convertToEntity(employee)).getEmpId();
	}

	@Override
	public void delete(Integer id) {
		employeeRepository.deleteById(id);
	}
	
	private EmployeeDTO convertToDto(Employee employee) {
		return modelMapper.map(employee, EmployeeDTO.class);
	}

	private Employee convertToEntity(EmployeeDTO employeeDTO) {
		return modelMapper.map(employeeDTO, Employee.class);
	}

}
