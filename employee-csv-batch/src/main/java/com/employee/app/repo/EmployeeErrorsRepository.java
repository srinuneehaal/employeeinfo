package com.employee.app.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.employee.app.entity.EmployeeErrors;

/**
 * @author srinu
 *
 */
@Repository
public interface EmployeeErrorsRepository extends JpaRepository<EmployeeErrors, Integer> {

}
