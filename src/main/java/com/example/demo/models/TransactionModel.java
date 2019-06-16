package com.example.demo.models;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.CreationTimestamp;

@Entity
public class TransactionModel{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	private UserModel fromAccount;

	@ManyToOne
	private UserModel toAccount;

	@Column
	private double amount;

	@Column
	private double fromAccountBalance;

	@Column
	private double toAccountBalance;

	@Column
	@CreationTimestamp
	private Timestamp timestamp;
	
	
	public TransactionModel() {
	}

	public TransactionModel(UserModel fromAccount, UserModel toAccount, double amount, double fromAccountBalance, double toAccountBalance) {
		this.fromAccount = fromAccount;
		this.toAccount = toAccount;
		this.amount = amount;
		this.fromAccountBalance = fromAccountBalance;
		this.toAccountBalance = toAccountBalance;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public UserModel getFromAccount() {
		return this.fromAccount;
	}

	public void setFromAccount(UserModel fromAccount) {
		this.fromAccount = fromAccount;
	}

	public UserModel getToAccount() {
		return this.toAccount;
	}

	public void setToAccount(UserModel toAccount) {
		this.toAccount = toAccount;
	}

	public double getAmount() {
		return this.amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public double getFromAccountBalance() {
		return this.fromAccountBalance;
	}

	public void setFromAccountBalance(double fromAccountBalance) {
		this.fromAccountBalance = fromAccountBalance;
	}

	public double getToAccountBalance() {
		return this.toAccountBalance;
	}

	public void setToAccountBalance(double toAccountBalance) {
		this.toAccountBalance = toAccountBalance;
	}

	public Timestamp getTimestamp() {
		return this.timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}
}