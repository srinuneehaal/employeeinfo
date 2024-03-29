package com.employee.app.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "employee_batch")
public class Employee {
//
	@Id
	private Long empID;
	private String firstName;
	private String lastName;
	private String email;
	private String gender;
		
	
}
