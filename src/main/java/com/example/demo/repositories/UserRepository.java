package com.example.demo.repositories;

import java.util.List;

import com.example.demo.models.UserModel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Long>{
	UserModel findByUsername(String username);
	UserModel findByaccountNumber(long accountNumber);
	UserModel findByphone(String phone);
	UserModel findByemail(String email);
	List<UserModel> findAllByEmail(String email);
	List<UserModel> findAllByPhone(String phone);
}