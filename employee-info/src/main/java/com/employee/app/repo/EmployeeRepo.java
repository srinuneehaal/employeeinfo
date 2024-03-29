package com.employee.app.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.employee.app.entity.Employee;

@Repository
public interface EmployeeRepo extends JpaRepository<Employee, Long> {

	public Employee findByEmpID(Long empID);

	public Employee findFirstByEmpIDAndEmpEmail(Long empID, String emailID);

	public Employee findFirstByEmpEmail(String emailID);
}
