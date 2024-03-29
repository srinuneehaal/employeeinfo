package com.employee.app.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.employee.app.entity.EmployeeBatchProcess;

/**
 * 
 * @author srinu
 *
 */
@Repository
public interface EmployeeBatchProcessRepository extends JpaRepository<EmployeeBatchProcess, Integer> {

}
