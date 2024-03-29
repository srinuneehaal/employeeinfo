package com.employee.app.config;

import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.employee.app.entity.Employee;
import com.employee.app.repo.EmployeeRepository;

/**
 * Custom writer to insert/update data to the database using jpa repo
 * @author srinu
 *
 */
@Component
public class EmployeeWriter implements ItemWriter<Employee>{

	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Override
	public void write(Chunk<? extends Employee> list) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("Writer Thread "+Thread.currentThread().getName());
        employeeRepository.saveAll(list);
	}
			 
}
