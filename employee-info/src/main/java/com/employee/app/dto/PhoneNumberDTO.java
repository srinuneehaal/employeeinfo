package com.employee.app.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PhoneNumberDTO {

	@JsonIgnore
	private int phoneID;

	 @Pattern(regexp = "^\\d{10}$",message = "invalid mobile number entered ")
	private String phoneNumber;

}
