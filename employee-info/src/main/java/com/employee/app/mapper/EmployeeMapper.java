package com.employee.app.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.employee.app.dto.EmployeeDTO;
import com.employee.app.entity.Employee;

@Mapper
public interface EmployeeMapper {

	EmployeeMapper INSTANCE = Mappers.getMapper(EmployeeMapper.class);

    Employee mapEmployeeDTOToEmployee(EmployeeDTO employeeDTO);
    EmployeeDTO mapEmployeeToEmployeeDTO(Employee employee);

}