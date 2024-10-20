package com.innogent.training.IService;

import java.util.List;
import java.util.Map;

import com.innogent.training.entity.Employee;
import com.innogent.training.model.EmployeeModel;

public interface IEmpService {
	public EmployeeModel getEmp(Long id);

	public EmployeeModel addEmp(Employee emp);

	public EmployeeModel updateEmp(Long id, EmployeeModel emp);

	public String deleteEmp(Long id);

	public List<Employee> getAllEmp();

	public EmployeeModel getEmpById(Long id);

	public List<EmployeeModel> getAllEmpOrderByName(String order);

	public List<EmployeeModel> getAllEmpOrderBySal(String order);

	public EmployeeModel getEmpByName(String name);

	public List<EmployeeModel> getEmpSalGreaterThan(Double sal);

	public List<EmployeeModel> getEmpSalLesserThan(Double sal);

	public String allMultipleEmp(List<EmployeeModel> empList);

	public List<EmployeeModel> getEmpByPage(Integer pagesize);

	public EmployeeModel getEmpBySal(Double sal);

	public List<EmployeeModel> getEmpOfAdd(String add);

	public Map<String, Integer> getEmpCountByAddress();

	public Map<String, Double> getEmpSalByAddress();

	public Employee addEmpDetails(Employee emp);

	public Employee getEmpDetailsById(Long id);

}
