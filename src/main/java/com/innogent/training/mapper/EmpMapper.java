package com.innogent.training.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.innogent.training.entity.Employee;
import com.innogent.training.model.EmployeeModel;

@Component
public class EmpMapper {
	private ModelMapper mapper;

	public EmpMapper() {
		mapper = new ModelMapper();
	}

	public EmployeeModel entityToModel(Employee emp) {
		return mapper.map(emp, EmployeeModel.class);
	}

	public Employee modelToEntity(EmployeeModel employee) {
		return mapper.map(employee, Employee.class);
	}

	public List<EmployeeModel> entityToModel(List<Employee> employees) {
		return employees.stream().map(this::entityToModel).collect(Collectors.toList());
	}

	public List<Employee> modelToEntity(List<EmployeeModel> empList) {
		return empList.stream().map(this::modelToEntity).collect(Collectors.toList());
	}
}
