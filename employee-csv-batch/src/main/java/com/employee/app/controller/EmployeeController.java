package com.employee.app.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Employee Job Launcher to load employee csv file and it will trigger everyday
 * at 11:30 AM
 * 
 * @author Srinivas P
 *
 */

//0 30 11 1/1 * ? *
@RestController
@RequestMapping("/jobs")
public class EmployeeController {

	private static final Logger log = LoggerFactory.getLogger(EmployeeController.class);

	@Autowired
	private JobLauncher jobLauncher;
	@Autowired
	private Job job;

	@PostMapping("/import")
//	@Scheduled(cron = "0 30 11 1/1 * ? *")
	public void importCsvToDBJob() {

		String fileName = "src/main/resources/employees.csv";
		log.info("importCsvToDBJob invoking with filename {}", fileName);
		System.out.println("File name-->" + fileName);
		JobParameters jobParameters = new JobParametersBuilder().addLong("startAt", System.currentTimeMillis())
				.addString("fileName", fileName).toJobParameters();

		try {

			jobLauncher.run(job, jobParameters);

		} catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException
				| JobParametersInvalidException e) {
			e.printStackTrace();
		}
	}

}
