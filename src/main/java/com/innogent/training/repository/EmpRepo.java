package com.innogent.training.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.innogent.training.entity.Employee;

public interface EmpRepo extends JpaRepository<Employee, Long> {

	public Optional<Employee> findByEmpName(String empName);

	public List<Employee> findBySalGreaterThan(Double sal);

	public List<Employee> findBySalLessThan(Double sal);

	@Query("SELECT e FROM Employee e WHERE e.sal = :sal")
	public Employee getEmpBySal(@Param("sal") Double sal);

	@Query("SELECT e FROM Employee e WHERE e.address= :add")
	public List<Employee> getEmpOfCity(@Param("add") String city);

	@Query("SELECT e.address, COUNT(e) FROM Employee e GROUP BY e.address")
	public List<Object[]> getEmpWithAddress();

	@Query("SELECT e.address, SUM(e.sal) FROM Employee e GROUP BY e.address")
	public List<Object[]> getEmpSalGroupByAddress();

}
