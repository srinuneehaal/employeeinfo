package com.employee.app.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.employee.app.controller.EmployeeController;
import com.employee.app.dto.EmployeeDTO;
import com.employee.app.entity.Employee;
import com.employee.app.exception.EmployeeNotFoundException;
import com.employee.app.mapper.EmployeeMapper;
import com.employee.app.repo.EmployeeRepo;

/**
 * service layer to implement bussiness logic
 * 
 * @author Srinivas P
 *
 */
@Service
public class EmployeeService {

	Logger logger = org.slf4j.LoggerFactory.getLogger(EmployeeService.class);

	@Autowired
	private EmployeeRepo employeeRepo;

	/**
	 * Method to return all the employee
	 * 
	 * @return
	 */
	public List<EmployeeDTO> getAllEmployee() {

		List<Employee> employeesList = employeeRepo.findAll();
		List<EmployeeDTO> employeesListDTO = employeesList.stream()
				.map(employee -> EmployeeMapper.INSTANCE.mapEmployeeToEmployeeDTO(employee))
				.collect(Collectors.toList());

//		return employeeRepo.findAll();

		return employeesListDTO;

	}

	/**
	 * Method to save the employee data
	 * 
	 * @param employeeDTO
	 * @return
	 */
	public EmployeeDTO saveEmployeeDetails(EmployeeDTO employeeDTO) {

		logger.info(" saveEmployeeDetails method employee " + employeeDTO);
		return EmployeeMapper.INSTANCE.mapEmployeeToEmployeeDTO(
				employeeRepo.save(EmployeeMapper.INSTANCE.mapEmployeeDTOToEmployee(employeeDTO)));

	}

	/**
	 * Method to return the employee information based on empoyeeid and emailid
	 * 
	 * @param employeeID
	 * @param emalID
	 * @return
	 * @throws EmployeeNotFoundException
	 */
	public EmployeeDTO getEmployeeDetailsByIDAndEmail(Long employeeID, String emalID) throws EmployeeNotFoundException {

		Employee employee = null;
		logger.info(" getEmployeeDetailsByIDAndEmail method employeeID :{} and emailID :{} ", employeeID, emalID);
		if (null != employeeID && null == emalID) {

			employee = employeeRepo.findByEmpID(employeeID);
		} else if (null == employeeID && null != emalID) {

			employee = employeeRepo.findFirstByEmpEmail(emalID);
		} else {

			employee = employeeRepo.findFirstByEmpIDAndEmpEmail(employeeID, emalID);
		}
		if (employee != null)
			return EmployeeMapper.INSTANCE.mapEmployeeToEmployeeDTO(employee);
		else
			throw new EmployeeNotFoundException("Employee Not found");
	}
}
