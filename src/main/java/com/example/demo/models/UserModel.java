package com.example.demo.models;

import java.sql.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.TableGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class UserModel{
	@TableGenerator(name = "accountNumber", initialValue = 10000000)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "accountNumber")
	private Long accountNumber;
	
	@Column(unique = true)
	private String username;

	@Column
	@JsonIgnore
	private String password;

	@Column
	private String name;

	@Column
	private boolean gender;	

	@Column(unique = true)
	private String email;

	@Column(unique = true)
	private String phone;

	@Column
	private Date dateOfBirth;

	@Column
	private double accountBalance;

	@OneToOne(cascade = CascadeType.ALL)
	private AddressModel addressModel;

	public UserModel() {
	}

	public UserModel(String username, String password, String name, boolean gender, String email, String phone, Date dateOfBirth, Long accountBalance, AddressModel addressModel) {
		this.username = username;
		this.password = password;
		this.name = name;
		this.gender = gender;
		this.email = email;
		this.phone = phone;
		this.dateOfBirth = dateOfBirth;
		this.accountBalance = accountBalance;
		this.addressModel = addressModel;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isGender() {
		return this.gender;
	}

	public boolean getGender() {
		return this.gender;
	}

	public void setGender(boolean gender) {
		this.gender = gender;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Date getDateOfBirth() {
		return this.dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public Long getAccountNumber() {
		return this.accountNumber;
	}

	public void setAccountNumber(Long accountNumber) {
		this.accountNumber = accountNumber;
	}

	public double getAccountBalance() {
		return this.accountBalance;
	}

	public void setAccountBalance(double accountBalance) {
		this.accountBalance = accountBalance;
	}

	public AddressModel getAddressModel() {
		return this.addressModel;
	}

	public void setAddressModel(AddressModel addressModel) {
		this.addressModel = addressModel;
	}
}