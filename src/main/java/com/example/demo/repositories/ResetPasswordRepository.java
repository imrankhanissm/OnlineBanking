package com.example.demo.repositories;

import com.example.demo.models.ResetPasswordModel;
import com.example.demo.models.UserModel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResetPasswordRepository extends JpaRepository<ResetPasswordModel, Long>{
	void deleteByUserModel(UserModel usermodel);
	ResetPasswordModel findByUserModelAndToken(UserModel userModel, String token);
}