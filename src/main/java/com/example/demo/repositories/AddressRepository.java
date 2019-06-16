package com.example.demo.repositories;

import com.example.demo.models.AddressModel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<AddressModel, Long>{
	
}