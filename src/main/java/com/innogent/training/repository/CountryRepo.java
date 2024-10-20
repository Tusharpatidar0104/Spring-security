package com.innogent.training.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.innogent.training.entity.Country;

public interface CountryRepo extends JpaRepository<Country, Integer> {

	@Query(value = "select * from country where country_name=:country", nativeQuery = true)
	Country findByCountryName(@Param("country") String Country);
}
