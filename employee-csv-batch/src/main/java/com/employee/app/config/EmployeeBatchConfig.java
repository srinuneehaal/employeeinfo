package com.employee.app.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.skip.SkipPolicy;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

import com.employee.app.entity.Employee;
import com.employee.app.listener.EmployeeSkipListener;

import lombok.AllArgsConstructor;

/**
 * Batch configuration for reader/process/writer and all the listeners
 * 
 * @author Srinivas P
 *
 */
@Configuration
@EnableBatchProcessing
@AllArgsConstructor
public class EmployeeBatchConfig {

	private static final Logger log = LoggerFactory.getLogger(EmployeeBatchConfig.class);

	private final JobRepository jobRepository;
	private final PlatformTransactionManager platformTransactionManager;

	/**
	 * 
	 * Reader to read csv file
	 * 
	 * @param fileName
	 * @return
	 */
	@Bean
	@StepScope
	public FlatFileItemReader<Employee> reader(@Value("#{jobParameters[fileName]}") String fileName) {

//			String fileName=getExecutionContext().get
		log.info("reader invoking with filename {}", fileName);
		FlatFileItemReader<Employee> itemReader = new FlatFileItemReader<>();
		itemReader.setResource(new FileSystemResource(fileName));
		itemReader.setName("csvReader");
		itemReader.setLinesToSkip(1);
		itemReader.setLineMapper(lineMapper());
		return itemReader;
	}

	/**
	 * registration of Process bean
	 * 
	 * @return
	 */
	@Bean
	public EmployeeProcessor processor() {
		return new EmployeeProcessor();
	}

	/**
	 * 
	 * @return
	 */
	@Bean
	public Step employeeStep() {
		log.info("employeeStep invoking with listeners ");
		return new StepBuilder("csvImport", jobRepository).<Employee, Employee>chunk(1000, platformTransactionManager)
				.reader(reader(null)).processor(processor()).writer(writer())
//				.faultTolerant()
//				.listener(employeeListener())
//				.skipPolicy(skipPolicy())
				// .listener(getEmployeeStepListener())
//				.taskExecutor(taskExecutor())
				.build();
	}

	/**
	 * skip policy for number format exception
	 * 
	 * @return
	 */
	@Bean
	public SkipPolicy skipPolicy() {
		return new ExceptionSkipPolicy();
	}

	/**
	 * Custom writer bean registration
	 * 
	 * @return
	 */
	@Bean
	public ItemWriter<? super Employee> writer() {
		// TODO Auto-generated method stub
		log.info("writer invoking with employee details ");
		return new EmployeeWriter();
	}

//	@Bean
//	  public EmployeeStepListener getEmployeeStepListener(){
//	       return new EmployeeStepListener();
//	    }

	/**
	 * bean registration for inserting batch process details
	 * 
	 * @return
	 */
	@Bean
	public JobCompletionNotificationListener getJobCompletionNotificationListener() {
		
		log.info("Listener invoking with employee details ");
		return new JobCompletionNotificationListener();
	}

	/**
	 * 
	 * bean registration for Job
	 * 
	 * @return
	 */
	@Bean
	public Job runJob() {
		log.info("Job invoking with employee details ");
		return new JobBuilder("Employee insert batch process", jobRepository)
				.listener(getJobCompletionNotificationListener()).start(employeeStep()).build();

	}

	/**
	 * Bean registration for TaskExecutor to process asynchronously with 10 threads
	 * 
	 * @return
	 */
	@Bean
	public TaskExecutor taskExecutor() {
		log.info("taskExecutor invoking with employee details ");
		SimpleAsyncTaskExecutor asyncTaskExecutor = new SimpleAsyncTaskExecutor();
		asyncTaskExecutor.setConcurrencyLimit(10);
		return asyncTaskExecutor;
	}

	/**
	 * bean registration for skipping records to invoke invalid records
	 * 
	 * @return
	 */
	@Bean
	public EmployeeSkipListener employeeSkipListener() {
		log.info("employeeSkipListener invoking with employee details ");
		return new EmployeeSkipListener();
	}

	/**
	 * @return
	 */
	@Bean
	public ExecutionContext getExecutionContext() {
		return new ExecutionContext();
	}

	/**
	 * line mapper for header and line tokenkenize with delimiter
	 * 
	 * @return
	 */
	private LineMapper<Employee> lineMapper() {
		log.info("lineMapper invoking with employee details ");
		DefaultLineMapper<Employee> lineMapper = new DefaultLineMapper<>();

		DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
		lineTokenizer.setDelimiter(",");
		lineTokenizer.setStrict(false);
		lineTokenizer.setNames("emp_id", "first_name", "last_name", "email", "gender");
		BeanWrapperFieldSetMapper<Employee> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
		fieldSetMapper.setTargetType(Employee.class);
		lineMapper.setLineTokenizer(lineTokenizer);
		lineMapper.setFieldSetMapper(fieldSetMapper);
		return lineMapper;
	}

}
