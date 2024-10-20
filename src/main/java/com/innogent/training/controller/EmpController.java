package com.innogent.training.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.innogent.training.IService.IEmpService;
import com.innogent.training.dto.AuthRequest;
import com.innogent.training.empServiceImpl.JwtService;
import com.innogent.training.entity.Employee;
import com.innogent.training.model.EmployeeModel;

@CrossOrigin
@RestController
@RequestMapping("/employee")
public class EmpController {
	@Autowired
	private IEmpService service;

	@Autowired
	private JwtService jwtService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@GetMapping("/all")
	public ResponseEntity<List<Employee>> getAllEmployee() {
		return ResponseEntity.status(HttpStatus.FOUND).body(service.getAllEmp());
	}
	@PostMapping("/add")
	public ResponseEntity<?> saveEmployeeModel(@RequestBody Employee employee) {
		try {
			EmployeeModel e = service.addEmp(employee);
			return ResponseEntity.status(HttpStatus.CREATED).body(e);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error occured while saving an EmployeeModel");
		}
	}
	@PutMapping("/update/{id}")
	public ResponseEntity<?> updateEmployeeModel(@PathVariable Long id, @RequestBody Employee EmployeeModel) {
		try {
			EmployeeModel e = service.getEmpById(id);
			if (e == null)
				return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

			EmployeeModel.setEmpId(id);
			EmployeeModel EmployeeModell = service.addEmp(EmployeeModel);
			return ResponseEntity.status(HttpStatus.OK).body(EmployeeModell);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Student Not Found");
		}
	}
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteEmployeeModel(@PathVariable Long id) {
		try {
			String s = service.deleteEmp(id);
			return ResponseEntity.status(HttpStatus.OK).body(s);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Student Not Found");
		}
	}
	@GetMapping("/get/order/name/{order}")
	public ResponseEntity<?> getAllEmployeeModelOrderByName(@PathVariable String order) {
		List<EmployeeModel> EmployeeModelList = service.getAllEmpOrderByName(order);
		if (EmployeeModelList == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not Found");
		}
		return ResponseEntity.status(HttpStatus.FOUND).body(EmployeeModelList);
	}
	@GetMapping("/get/order/sal/{order}")
	public ResponseEntity<?> getAllEmployeeModelOrderBySal(@PathVariable String order) {
		List<EmployeeModel> EmployeeModelList = service.getAllEmpOrderBySal(order);
		if (EmployeeModelList == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not Found");
		}
		return ResponseEntity.status(HttpStatus.FOUND).body(EmployeeModelList);
	}
	@GetMapping("/get/EmployeeModel/{name}")
	public ResponseEntity<?> getEmployeeModelByName(@PathVariable String name) {
		EmployeeModel e = service.getEmpByName(name);
		if (e == null)
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not Found");
		return ResponseEntity.status(HttpStatus.FOUND).body(e);
	}
	@GetMapping("/get/sal/greater/{sal}")
	public ResponseEntity<?> getSalGreaterThan(@PathVariable Double sal) {
		List<EmployeeModel> EmployeeModelList = service.getEmpSalGreaterThan(sal);
		if (EmployeeModelList == null)
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not Found");
		return ResponseEntity.status(HttpStatus.FOUND).body(EmployeeModelList);
	}
	@GetMapping("/get/sal/lesser/{sal}")
	public ResponseEntity<?> getSalLesserThan(@PathVariable Double sal) {
		List<EmployeeModel> EmployeeModelList = service.getEmpSalLesserThan(sal);
		if (EmployeeModelList == null)
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not Found");
		return ResponseEntity.status(HttpStatus.FOUND).body(EmployeeModelList);
	}
	@PostMapping("/add/all")
	public String addMultipleEmployeeModel(@RequestBody List<EmployeeModel> EmployeeModelList) {
		return service.allMultipleEmp(EmployeeModelList);
	}
	@GetMapping("/get/emp/{size}")
	public List<EmployeeModel> getEmpByPage(@PathVariable Integer size) {
		return service.getEmpByPage(size);
	}
	@GetMapping("/get/sal/{sal}")
	public ResponseEntity<?> getEmpBysal(@PathVariable Double sal) {
		EmployeeModel emp = service.getEmpBySal(sal);
		if (emp == null)
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("There is No Employee with salary " + sal);
		return ResponseEntity.status(HttpStatus.OK).body(emp);
	}
	@GetMapping("/get/address/{add}")
	public ResponseEntity<?> getEmpByAdd(@PathVariable String add) {
		List<EmployeeModel> emp = service.getEmpOfAdd(add);
		if (emp == null)
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("There is No Employee with address " + add);
		return ResponseEntity.status(HttpStatus.OK).body(emp);
	}
	@GetMapping("/get/group/address/count")
	public ResponseEntity<?> getEmpCountGroupAddress() {
		try {
			Map<String, Integer> empMap = service.getEmpCountByAddress();
			return ResponseEntity.status(HttpStatus.FOUND).body(empMap);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.FOUND).body(e.getMessage());

		}
	}
	@GetMapping("/get/group/address/sal")
	public ResponseEntity<?> getEmpSalGroupAddress() {
		Map<String, Double> empMap = service.getEmpSalByAddress();
		return ResponseEntity.status(HttpStatus.FOUND).body(empMap);
	}
	
	@PostMapping("/addAllDetails")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<?> addEmpDetails(@RequestBody Employee e) {
		Employee emp = service.addEmpDetails(e);
		return ResponseEntity.status(HttpStatus.FOUND).body(e);
	}
	@GetMapping("/id/{id}")
	@PreAuthorize("hasAuthority('ROLE_EMPLOYEE') or hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<?> getEmployeeDetailsById(@PathVariable Long id) {
//		EmployeeModel e = service.getEmpById(id);
//		if (e != null) {
//			return ResponseEntity.status(HttpStatus.FOUND).body(e);
//		} else {
//			return ResponseEntity.notFound().build();
//		}
		Employee e = service.getEmpDetailsById(id);
		return ResponseEntity.status(HttpStatus.FOUND).body(e);

	}
	
	//to generate jwt token for authenticated user
	@PostMapping("/authenticate")
	public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
		if (authentication.isAuthenticated()) {
			return jwtService.generateToken(authRequest.getUsername());
		} else {
			throw new UsernameNotFoundException("invalid user request !");
		}

	}

	@GetMapping("/get/all")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<List<Employee>> getAllEmployeeForAdmin() {
		List<Employee> EmployeeModelList = service.getAllEmp();
		return ResponseEntity.status(HttpStatus.FOUND).body(EmployeeModelList);
	}
	
	@GetMapping("/{id}")
	@PreAuthorize("hasAuthority('ROLE_EMPLOYEE') or hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<?> getEmployeeById(@PathVariable Long id) {
		EmployeeModel e = service.getEmpById(id);
//		if (e != null) {
//			return ResponseEntity.status(HttpStatus.FOUND).body(e);
//		} else {
//			return ResponseEntity.notFound().build();
//		}
		return ResponseEntity.status(HttpStatus.FOUND).body(e);
	}

}
