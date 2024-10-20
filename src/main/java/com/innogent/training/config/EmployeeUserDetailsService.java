package com.innogent.training.config;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.innogent.training.entity.Employee;
import com.innogent.training.repository.EmpRepo;

@Component
public class EmployeeUserDetailsService implements UserDetailsService {
	@Autowired
	private EmpRepo repository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Employee> userInfo = repository.findByEmpName(username);
		return userInfo.map(EmployeeUserDetails::new)
				.orElseThrow(() -> new UsernameNotFoundException("Employee not found " + username));
	}
}
