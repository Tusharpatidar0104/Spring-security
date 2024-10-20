package com.innogent.training.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.innogent.training.entity.States;

public interface StatesRepo extends JpaRepository<States, Integer> {

	@Query(value = "select * from states where state_name=:state", nativeQuery = true)
	States findByStateName(@Param("state") String state);
}
