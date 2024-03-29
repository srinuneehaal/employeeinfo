package com.employee.app.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.SkipListener;
import org.springframework.beans.factory.annotation.Autowired;

import com.employee.app.entity.Employee;
import com.employee.app.entity.EmployeeErrors;
import com.employee.app.repo.EmployeeErrorsRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.SneakyThrows;

public class EmployeeSkipListener implements SkipListener<Employee, Number> {

	@Autowired
	EmployeeErrorsRepository errorsRepository;

	private static final Logger logger = LoggerFactory.getLogger(EmployeeSkipListener.class);

	/**
	 * item reader
	 */
	@Override
	public void onSkipInRead(Throwable throwable) {
		logger.info("A failure on read {} ", throwable.getMessage());
	}

	/**
	 * item writter
	 */
	@Override
	public void onSkipInWrite(Number item, Throwable throwable) {
		logger.info("A failure on write {} , {}", throwable.getMessage(), item);
	}

	/**
	 * item processor
	 */
	@SneakyThrows
	@Override
	public void onSkipInProcess(Employee employee, Throwable throwable) {
		logger.info("Item {}  was skipped due to the exception  {}", new ObjectMapper().writeValueAsString(employee),
				throwable.getMessage());

		// String fileName = jobExecution.getJobParameters().getString("fileName");

		System.out.println(" Listener employee.toString()-->" + employee.toString());
		EmployeeErrors employeeErrors = EmployeeErrors.builder().filename("employee.csv")
				.message("Error \r\n" + "processing Employee ID:" + employee.getEmpID())
				.errorFieldName("errorFieldName").build();
		errorsRepository.save(employeeErrors);

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
