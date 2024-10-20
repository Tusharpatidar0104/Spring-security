package com.innogent.training.empServiceImpl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.innogent.training.IService.IEmpService;
import com.innogent.training.entity.City;
import com.innogent.training.entity.Country;
import com.innogent.training.entity.Employee;
import com.innogent.training.entity.States;
import com.innogent.training.mapper.EmpMapper;
import com.innogent.training.model.EmployeeModel;
import com.innogent.training.repository.CityRepo;
import com.innogent.training.repository.CountryRepo;
import com.innogent.training.repository.EmpRepo;
import com.innogent.training.repository.StatesRepo;

@Service
public class EmpServiceImpl implements IEmpService {

	@Autowired
	private EmpRepo repo;

	@Autowired
	private EmpMapper mapper;

	@Autowired
	private CityRepo cityRepo;

	@Autowired
	private StatesRepo stateRepo;

	@Autowired
	private CountryRepo countryRepo;

	@Override
	public EmployeeModel getEmp(Long id) {
		return mapper.entityToModel(repo.findById(id).get());
	}

	@Override
	public EmployeeModel addEmp(Employee emp) {
//		Employee empEntity = mapper.modelToEntity(employee);
//		Employee empResult = repo.save(empEntity);
//		return mapper.entityToModel(empResult);
		emp.setPassword(new BCryptPasswordEncoder().encode(emp.getPassword()));
		emp.setRole("ROLE_EMPLOYEE");
		
		String existingCityName = emp.getAddress().getCity().getCityName();
		City existingCity = cityRepo.findByCityName(existingCityName);

		String existingStateName = emp.getAddress().getCity().getState().getStateName();
		States existingState = stateRepo.findByStateName(existingStateName);

		String existingCountryName = emp.getAddress().getCity().getState().getCountry().getCountryName();
		Country existingCountry = countryRepo.findByCountryName(existingCountryName);

		if (existingCity == null) {
			if (existingState == null) {
				if (existingCountry == null) {
					return mapper.entityToModel(repo.save(emp));
				}
				emp.getAddress().getCity().getState().setCountry(existingCountry);
				return mapper.entityToModel(repo.save(emp));
			}
			emp.getAddress().getCity().setState(existingState);
			return mapper.entityToModel(repo.save(emp));
		}
		emp.getAddress().setCity(existingCity);
		return mapper.entityToModel(repo.save(emp));
	}

	@Override
	public EmployeeModel updateEmp(Long id, EmployeeModel emp) {
		emp.setEmpId(id);
		Employee e = mapper.modelToEntity(emp);
		return mapper.entityToModel(repo.save(e));
	}

	@Override
	public String deleteEmp(Long id) {
		Employee e = repo.findById(id).get();
		if (e == null)
			return "Student Not Found";
		repo.deleteById(id);
		return "Student Deleted";
	}

	@Override
	public List<Employee> getAllEmp() {
		return repo.findAll();
	}

	@Override
	public EmployeeModel getEmpById(Long id) {
		Employee e = repo.findById(id).get();
		if (e != null)
			return mapper.entityToModel(e);
		return null;
	}

	@Override
	public List<EmployeeModel> getAllEmpOrderByName(String order) {
		if (order.equals("asc"))
			return mapper.entityToModel(repo.findAll(Sort.by(Direction.ASC, "empName")));
		else if (order.equals("desc"))
			return mapper.entityToModel(repo.findAll(Sort.by(Direction.DESC, "empName")));
		return null;
	}

	@Override
	public List<EmployeeModel> getAllEmpOrderBySal(String order) {
		if (order.equals("asc"))
			return mapper.entityToModel(repo.findAll(Sort.by(Direction.ASC, "sal")));
		else if (order.equals("asc"))
			return mapper.entityToModel(repo.findAll(Sort.by(Direction.DESC, "sal")));
		return null;
	}

	@Override
	public EmployeeModel getEmpByName(String name) {
		return mapper.entityToModel(repo.findByEmpName(name).get());
	}

	@Override
	public List<EmployeeModel> getEmpSalGreaterThan(Double sal) {
		return mapper.entityToModel(repo.findBySalGreaterThan(sal));
	}

	@Override
	public List<EmployeeModel> getEmpSalLesserThan(Double sal) {
		return mapper.entityToModel(repo.findBySalLessThan(sal));
	}

	@Override
	public String allMultipleEmp(List<EmployeeModel> empList) {
		repo.saveAll(mapper.modelToEntity(empList));
		return "All Employees Added";
	}

	@Override
	public List<EmployeeModel> getEmpByPage(Integer pagesize) {
		Pageable p = PageRequest.of(1, pagesize, Sort.by(Direction.ASC, "sal"));
		Page<Employee> pageList = repo.findAll(p);
		List<Employee> empList = pageList.getContent();
		return mapper.entityToModel(empList);
	}

	@Override
	public EmployeeModel getEmpBySal(Double sal) {
		Employee e = repo.getEmpBySal(sal);
		if (e == null)
			return null;
		return mapper.entityToModel(e);
	}

	@Override
	public List<EmployeeModel> getEmpOfAdd(String add) {
		List<Employee> e = repo.getEmpOfCity(add);
		if (e == null)
			return null;
		return mapper.entityToModel(e);
	}

	@Override
	public Map<String, Integer> getEmpCountByAddress() {
		List<Object[]> o = repo.getEmpWithAddress();
		return o.stream().collect(Collectors.toMap(a -> (String) a[0], a -> Integer.valueOf(a[1] + "")));
	}

	@Override
	public Map<String, Double> getEmpSalByAddress() {
		List<Object[]> o = repo.getEmpSalGroupByAddress();
		return o.stream().collect(Collectors.toMap(a -> (String) a[0], a -> Double.valueOf(a[1] + "")));
	}

	@Override
	public Employee addEmpDetails(Employee emp) {
		
		emp.setPassword(new BCryptPasswordEncoder().encode(emp.getPassword()));
		emp.setRole("ROLE_EMPLOYEE");
		
		String existingCityName = emp.getAddress().getCity().getCityName();
		City existingCity = cityRepo.findByCityName(existingCityName);

		String existingStateName = emp.getAddress().getCity().getState().getStateName();
		States existingState = stateRepo.findByStateName(existingStateName);

		String existingCountryName = emp.getAddress().getCity().getState().getCountry().getCountryName();
		Country existingCountry = countryRepo.findByCountryName(existingCountryName);

		if (existingCity == null) {
			if (existingState == null) {
				if (existingCountry == null) {
					return repo.save(emp);
				}
				emp.getAddress().getCity().getState().setCountry(existingCountry);
				return repo.save(emp);
			}
			emp.getAddress().getCity().setState(existingState);
			return repo.save(emp);
		}
		emp.getAddress().setCity(existingCity);
		return repo.save(emp);
	}

	@Override
	public Employee getEmpDetailsById(Long id) {
		Employee e = repo.findById(id).get();
//		if (e != null)
//			return mapper.entityToModel(e);
		return e;
	}

}
