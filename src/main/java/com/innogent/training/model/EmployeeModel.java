package com.innogent.training.model;

import lombok.Data;

@Data
public class EmployeeModel {
	private Long empId;
	private String empName;
	private String job;
	private Double sal;
	private String address;
}
