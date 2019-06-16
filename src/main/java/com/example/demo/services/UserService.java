package com.example.demo.services;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.models.TransactionModel;
import com.example.demo.models.TransactionOutputModel;
import com.example.demo.models.UserModel;
import com.example.demo.repositories.TransactionRepository;
import com.example.demo.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UserService{
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private TransactionRepository transactionRepository;

	public boolean deposite(String username, double amount){
		UserModel userModel = userRepository.findByUsername(username);
		if(userModel == null)	return false;
		userModel.setAccountBalance(userModel.getAccountBalance()+amount);
		userRepository.save(userModel);
		TransactionModel transactionModel = new TransactionModel(userModel, userModel, amount, userModel.getAccountBalance(), userModel.getAccountBalance());
		transactionRepository.save(transactionModel);
		return true;
	}

	public boolean withdraw(String username, double amount){
		UserModel userModel = userRepository.findByUsername(username);
		if(userModel == null)	return false;
		double accountBalance = userModel.getAccountBalance();
		if(amount > accountBalance)	return false;
		userModel.setAccountBalance(accountBalance-amount);
		userRepository.save(userModel);
		TransactionModel transactionModel = new TransactionModel(userModel, userModel, -amount, userModel.getAccountBalance(), userModel.getAccountBalance());
		transactionRepository.save(transactionModel);
		return true;
	}

	public boolean transfer(String username, long toAccountNumber, double amount){
		UserModel userModel = userRepository.findByUsername(username);
		UserModel toUserModel = userRepository.findByaccountNumber(toAccountNumber);
		if(userModel == null || toUserModel == null)	return false;
		double accountBalance = userModel.getAccountBalance();
		if(amount > accountBalance)	return false;
		userModel.setAccountBalance(accountBalance-amount);
		toUserModel.setAccountBalance(toUserModel.getAccountBalance()+amount);
		userRepository.save(userModel);
		userRepository.save(toUserModel);
		TransactionModel transactionModel = new TransactionModel(userModel, toUserModel, amount, userModel.getAccountBalance(), toUserModel.getAccountBalance());
		transactionRepository.save(transactionModel);
		return true;
	}

	public List<TransactionOutputModel> history(String username, int page, int limit){
		UserModel userModel = userRepository.findByUsername(username);
		if(userModel == null)	return null;
		Pageable pageable = PageRequest.of(page, limit);
		List<TransactionModel> list = transactionRepository.findByFromAccountOrToAccountOrderByTimestampDesc(userModel, userModel, pageable);
		// List<TransactionModel> list = transactionRepository.findByFromAccountOrToAccountOrderByTimestampDesc(userModel, userModel);
		List<TransactionOutputModel> listOutput = new ArrayList<>();
		for (TransactionModel transactionModel : list) {
			String type;
			double amount = transactionModel.getAmount();
			double accountBalance;
			long fromAccountNumber = transactionModel.getFromAccount().getAccountNumber();
			long toAccountNumber = transactionModel.getToAccount().getAccountNumber();

			if(fromAccountNumber == toAccountNumber){
				accountBalance = transactionModel.getFromAccountBalance();
				if(amount > 0){
					type = "Deposite";
				}else{
					type = "Withdraw";
				}
			}else{
				if(userModel.getAccountNumber() == fromAccountNumber){
					accountBalance = transactionModel.getFromAccountBalance();
					type = "Transfer to " + toAccountNumber;
					amount = -amount;
				}
				else{
					accountBalance = transactionModel.getToAccountBalance();
					type = "Transfer from " + fromAccountNumber;
				}
			}
			listOutput.add(new TransactionOutputModel(transactionModel.getId(), type, amount, accountBalance, transactionModel.getTimestamp()));
		}
		return listOutput;
	}
}

