package com.employee.app.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "employee")
public class Employee {

	@Id
	@Column(name = "emp_id")
	private Long empID;

	@Column(name = "emp_first_name")
	private String empFirstName;

	@Column(name = "emp_last_name")
	private String empLastName;

	@Column(name = "emp_email")
	private String empEmail;

	@OneToMany(targetEntity = PhoneNumber.class, cascade = CascadeType.ALL)
	@JoinColumn(name = "emp_id")
	private List<PhoneNumber> phoneNumbers;

	@Column(name = "emp_doj")
	private String empDateOfJoin;

	@Column(name = "emp_sal")
	private Double empSalary;

}
