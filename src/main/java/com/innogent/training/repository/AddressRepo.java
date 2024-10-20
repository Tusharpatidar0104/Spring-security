package com.innogent.training.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.innogent.training.entity.Address;

public interface AddressRepo extends JpaRepository<Address, Integer> {

}
