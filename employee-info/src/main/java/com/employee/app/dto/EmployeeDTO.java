package com.employee.app.dto;

import java.util.List;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
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
public class EmployeeDTO {

	@NotNull(message = "Employee ID shouldn't be null")
	private int empID;

	@NotNull(message = "Employee First Name shouldn't be null")
	private String empFirstName;

	@NotNull(message = "Employee Last Name shouldn't be null")
	private String empLastName;

	@Email(message = "Invalid Employee email address")
	private String empEmail;

	@NotNull(message = "Employee Phone Number shouldn't be null")
	private List<PhoneNumberDTO> phoneNumbers;

	@NotNull(message = "Employee Date of Join shouldn't be null")
	private String empDateOfJoin;

	@NotNull(message = "Employee Salary shouldn't be null")
	private Double empSalary;

}