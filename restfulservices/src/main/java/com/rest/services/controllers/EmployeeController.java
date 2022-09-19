package com.rest.services.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rest.services.EmployeeService;
import com.rest.services.model.EmployeeDTO;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/api/employees")
@RequiredArgsConstructor
@Validated
public class EmployeeController {

	@Autowired
	private final EmployeeService employeeService;

	@GetMapping(value = "/", produces = "application/json")
	public List<EmployeeDTO> findAll() {
		return employeeService.findAllEmployees();
	}

	@GetMapping(value = "/{id}", produces = "application/json")
	public EmployeeDTO findEmployee(@PathVariable Integer id) {
		return employeeService.findEmployeeById(id);
	}

	@DeleteMapping(path = { "/{id}" })
	public void delete(@PathVariable("id") Integer id) {
		employeeService.delete(id);
		return;
	}

	@PostMapping
	public Integer create(@RequestBody @Valid EmployeeDTO employee) {
		return employeeService.saveOrUpdate(employee);
	}

	@PatchMapping(path = "/{id}")
	public Integer update(@PathVariable @Valid Integer id, @RequestBody @Valid EmployeeDTO employee) {
		if (!id.equals(employee.getEmpId())) {
			throw new IllegalArgumentException("Employee id not matched with path variable");
		}
		return employeeService.saveOrUpdate(employee);
	}
}
