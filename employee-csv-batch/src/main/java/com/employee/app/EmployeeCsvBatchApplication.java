package com.employee.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author srinu
 *
 */
@SpringBootApplication
//@EnableScheduling
public class EmployeeCsvBatchApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmployeeCsvBatchApplication.class, args);
	}

}
