package com.employee.app.controller;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.employee.app.dto.EmployeeDTO;
import com.employee.app.exception.EmployeeNotFoundException;
import com.employee.app.service.EmployeeService;

import jakarta.validation.Valid;

/**
 * Controller to handle the Rest endpoints
 * 
 * @author Srinivas
 *
 */
@RestController
@RequestMapping("/employee")
public class EmployeeController {

	Logger logger = org.slf4j.LoggerFactory.getLogger(EmployeeController.class);

	@Autowired
	EmployeeService employeeService;

	/**
	 * Returns all the employees
	 * 
	 * @return
	 */
	@GetMapping("/all")
	public ResponseEntity<List<EmployeeDTO>> getAllEmployee() {
		logger.info("getAllEmployee method");
		return ResponseEntity.ok().body(employeeService.getAllEmployee());
	}

	/**
	 * Method to save the employee data
	 * 
	 * @param employeeDTO
	 * @return
	 */
	@PostMapping
	public ResponseEntity<EmployeeDTO> saveEmployee(@RequestBody @Valid EmployeeDTO employeeDTO) {
		return ResponseEntity.ok().body(employeeService.saveEmployeeDetails(employeeDTO));
	}

	/**
	 * Method to return the employee information based on empoyeeid and emailid
	 * 
	 * @param employeeID
	 * @param emalID
	 * @return
	 * @throws EmployeeNotFoundException
	 */
	@GetMapping
	public ResponseEntity<EmployeeDTO> getAllEmployee(@RequestParam(required = false, name = "empid") Long employeeID,
			@RequestParam(required = false, name = "emailid") String emalID) throws EmployeeNotFoundException {

		return ResponseEntity.ok().body(employeeService.getEmployeeDetailsByIDAndEmail(employeeID, emalID));

	}

}
