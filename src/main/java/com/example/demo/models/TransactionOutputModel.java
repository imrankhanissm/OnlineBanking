package com.example.demo.models;

import java.sql.Timestamp;

public class TransactionOutputModel{
	private Long id;
	private String type;
	private double amount;
	private double accountBalance;
	private Timestamp timestamp;

	public TransactionOutputModel() {
	}	
	
	public TransactionOutputModel(Long id, String type, double amount,double accountBalance, Timestamp timestamp) {
		this.id = id;
		this.type = type;
		this.amount = amount;
		this.accountBalance = accountBalance;
		this.timestamp = timestamp;
	}
	
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public double getAmount() {
		return this.amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public double getAccountBalance() {
		return this.accountBalance;
	}

	public void setAccountBalance(double accountBalance) {
		this.accountBalance = accountBalance;
	}

	public Timestamp getTimestamp() {
		return this.timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}
}