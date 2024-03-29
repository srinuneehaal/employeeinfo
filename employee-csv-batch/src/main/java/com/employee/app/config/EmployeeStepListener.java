package com.employee.app.config;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.employee.app.entity.EmployeeBatchProcess;
import com.employee.app.repo.EmployeeBatchProcessRepository;

//@Component
public class EmployeeStepListener implements StepExecutionListener {

	@Autowired
	private EmployeeBatchProcess employeeBatchProcess;

	@Autowired
	private EmployeeBatchProcessRepository batchProcessRepository;

	/**
	 *
	 */
	@Override
	public void beforeStep(StepExecution stepExecution) {
		// TODO Auto-generated method stub
//		StepExecutionListener.super.beforeStep(stepExecution);
		System.out.println("Before Step Execution");
		System.out.println("Insert count of records--" + stepExecution.getStepName());
		System.out.println("Insert count of read count records--" + stepExecution.getReadCount());
		System.out.println("Insert count of records--" + stepExecution.getSkipCount());
		String summary = stepExecution.getSummary();
		employeeBatchProcess = EmployeeBatchProcess.builder().processName(stepExecution.getStepName())
				.startTimestamp(stepExecution.getStartTime()).endTimestamp(stepExecution.getEndTime())
				.processedFileName("employees.csv").insertedRecordCount(stepExecution.getReadCount())
				.updateRecordCount(stepExecution.getReadCount() - stepExecution.getRollbackCount())
				.errorRecordCount(stepExecution.getRollbackCount()).build();
		System.out.println("employeeBatchProcess-->" + employeeBatchProcess.toString());
		employeeBatchProcess = batchProcessRepository.save(employeeBatchProcess);

	}

	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		// TODO Auto-generated method stub
		System.out.println("After Step Execution");
		System.out.println("Insert count of records--getStepName" + stepExecution.getStepName());
		System.out.println("Insert count of read count records--getReadCount" + stepExecution.getReadCount());
		System.out.println("Insert count of records--getSkipCount" + stepExecution.getSkipCount());
		System.out.println("Insert count of records--getSummary" + stepExecution.getSummary());
		employeeBatchProcess
//		.processName(stepExecution.getStepName())
//		.startTimestamp(stepExecution.getStartTime())
				.setEndTimestamp(stepExecution.getEndTime());
//		.processedFileName("employees.csv")
		employeeBatchProcess.setInsertedRecordCount(stepExecution.getReadCount());
		employeeBatchProcess.setUpdateRecordCount(stepExecution.getCommitCount());
		employeeBatchProcess.setErrorRecordCount(stepExecution.getRollbackCount());

		System.out.println("employeeBatchProcess-->after step-->" + employeeBatchProcess.toString());
		batchProcessRepository.save(employeeBatchProcess);

		if (ExitStatus.COMPLETED.equals(stepExecution.getExitStatus())) {
			System.out.println("The step has finished with status: {}" + stepExecution.getExitStatus());
			return stepExecution.getExitStatus();
		}
		System.out.println("Something bad has happened after step: {}" + stepExecution.getExitStatus());
		return stepExecution.getExitStatus();

//		return StepExecutionListener.super.afterStep(stepExecution);
	}

}
