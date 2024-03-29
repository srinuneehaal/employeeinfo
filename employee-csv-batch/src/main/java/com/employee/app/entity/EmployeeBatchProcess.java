package com.employee.app.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
@Entity
@Table(name = "batch_process")
public class EmployeeBatchProcess {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Column(name = "process_name")
	private String processName;

	@Column(name = "start_timestamp")
	private LocalDateTime startTimestamp;

	@Column(name = "end_timestamp")
	private LocalDateTime endTimestamp;

	@Column(name = "processed_file_name")
	private String processedFileName;

	@Column(name = "inserted_record_count")
	private Long insertedRecordCount;

	@Column(name = "updated_record_count")
	private Long updateRecordCount;

	@Column(name = "errored_record_count")
	private Long errorRecordCount;

}
