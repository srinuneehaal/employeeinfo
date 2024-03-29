package com.employee.app.config;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.StepExecution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.employee.app.entity.EmployeeBatchProcess;
import com.employee.app.repo.EmployeeBatchProcessRepository;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * Class for inserting batch process details
 * @author srinu
 *
 */
@Component
@AllArgsConstructor
@NoArgsConstructor
public class JobCompletionNotificationListener implements JobExecutionListener {

//	private static final Logger log = LoggerFactory.getLogger(JobCompletionNotificationListener.class);

	@Autowired
	private EmployeeBatchProcessRepository batchProcessRepository;

	@Autowired
	private EmployeeBatchProcess employeeBatchProcess;

	@Bean
	public EmployeeBatchProcess getEmployeeBatchProcess() {
		return new EmployeeBatchProcess();
	}

	/**
	 *
	 */
	@Override
	public void beforeJob(JobExecution jobExecution) {
		employeeBatchProcess.setProcessName(jobExecution.getJobInstance().getJobName());
		employeeBatchProcess.setStartTimestamp(jobExecution.getStartTime());
		employeeBatchProcess = batchProcessRepository.save(employeeBatchProcess);
	}

	/**
	 *
	 */
	@Override
	public void afterJob(JobExecution jobExecution) {
		
		StepExecution stepExecution = jobExecution.getStepExecutions().stream().findFirst().get();
		String fileName = jobExecution.getJobParameters().getString("fileName");

		employeeBatchProcess.setEndTimestamp(stepExecution.getEndTime());
		employeeBatchProcess.setProcessedFileName(fileName);
		employeeBatchProcess.setInsertedRecordCount(stepExecution.getWriteCount());
		employeeBatchProcess.setUpdateRecordCount(0L);
		employeeBatchProcess.setErrorRecordCount(stepExecution.getFilterCount());
		System.out.println("employeeBatchProcess-->after step-->" + employeeBatchProcess.toString());
		batchProcessRepository.save(employeeBatchProcess);

		
	}
}