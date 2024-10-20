package com.innogent.training.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.innogent.training.entity.City;

public interface CityRepo extends JpaRepository<City, Integer> {

	@Query(value = "select * from city where city_name=:city", nativeQuery = true)
	City findByCityName(@Param("city") String city);

}
