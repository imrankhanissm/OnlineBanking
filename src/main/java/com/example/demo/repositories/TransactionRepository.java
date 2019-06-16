package com.example.demo.repositories;

import java.util.List;

import com.example.demo.models.TransactionModel;
import com.example.demo.models.UserModel;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionModel, Long>{
	// List<TransactionModel> findByFromAccountOrToAccountOrderByTimestampDesc(UserModel from, UserModel to);
	List<TransactionModel> findByFromAccountOrToAccountOrderByTimestampDesc(UserModel from, UserModel to, Pageable pageable);
}