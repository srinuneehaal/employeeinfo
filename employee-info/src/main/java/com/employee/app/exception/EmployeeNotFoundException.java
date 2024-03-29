package com.employee.app.exception;

import org.slf4j.Logger;

/**
 * @author Srinivas P
 *
 */
public class EmployeeNotFoundException extends Exception {

	Logger logger = org.slf4j.LoggerFactory.getLogger(EmployeeNotFoundException.class);

	public EmployeeNotFoundException(String message) {
		// TODO Auto-generated constructor stub
		super(message);
		logger.error("EmployeeNotFoundException :{}", message);

	}
}
