package com.employee.app.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import com.employee.app.entity.Employee;
import com.employee.app.entity.EmployeeErrors;
import com.employee.app.repo.EmployeeErrorsRepository;

/**
 * Employee processor
 * 
 * @author Srinivas P
 *
 */
public class EmployeeProcessor implements ItemProcessor<Employee, Employee> {

	@Autowired
	EmployeeErrorsRepository employeeErrorsRepository;

	private static final Logger log = LoggerFactory.getLogger(EmployeeProcessor.class);

	/**
	 * Method to process the employee item and checking the gender
	 */
	@Override
	public Employee process(Employee employee) throws Exception {

		log.info("Employee processor to check the gender: " + employee);
		// TODO Auto-generated method stub
		if (null != employee && (employee.getGender().equals("Female") || employee.getGender().equals("Male"))) {

			return employee;
		} else {
			EmployeeErrors employeeError = mapToEmployeeError(employee, "Gender");
			System.out.println(" Processor employee.toString()-->" + employee.toString());
			employeeErrorsRepository.save(employeeError);
			return null;
		}
	}

	/**
	 * Method to map the employee object to store into employeeError
	 * 
	 * @param employee
	 * @param errorFieldName
	 * @return
	 */
	private EmployeeErrors mapToEmployeeError(Employee employee, String errorFieldName) {
		// TODO Auto-generated method stub

		EmployeeErrors employeeError = new EmployeeErrors();
		employeeError.setFilename("employee.csv");
		employeeError.setMessage("Error processing Employee ID: " + employee.getEmpID());
		employeeError.setErrorFieldName(errorFieldName);
		return employeeError;
	}

}