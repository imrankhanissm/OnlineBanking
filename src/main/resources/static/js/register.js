function validate(){
	var res = true;
	var username = document.getElementById("usernameRegister").value;
	var password = document.getElementById("passwordRegister").value;
	var confirmPassword = document.getElementById("confirmPasswordRegister").value;
	var name = document.getElementById("nameRegister").value;
	var gender = document.getElementsByName("gender")[0].checked || document.getElementsByName("gender")[1].checked;
	var dateOfBirth = document.getElementById("dateOfBirthRegister").value;
	var email = document.getElementById("emailRegister").value;
	var phone = document.getElementById("phoneRegister").value;
	var country = document.getElementById("countryRegister").value;
	var pincode = document.getElementById("pincodeRegister").value;
	var street = document.getElementById("streetRegister").value;
	var doorNo = document.getElementById("doorNoRegister").value;

	if(username.trim().length == 0){
		document.getElementById("usernameError").innerHTML = "username required";
		res = false;
	}
	if(password.trim().length == 0){
		document.getElementById("passwordError").innerHTML = "password required";
		res = false;
	}else if(!(/[a-z]/.test(password) && /[A-Z]/.test(password) && /[0-9]/.test(password))){
		document.getElementById("passwordError").innerHTML = "pattern not found";
		res = false;
	}else if(password != confirmPassword){
		document.getElementById("confirmPasswordError").innerHTML = "Password doesn't match";
		res = false;
	}
	if(name.trim().length == 0){
		document.getElementById("nameError").innerHTML = "name required";
		res = false;
	}
	if(!gender){
		document.getElementById("genderError").innerHTML = "gender required";
		res = false;
	}
	if(dateOfBirth.trim().length == 0){
		document.getElementById("dobError").innerHTML = "dateOfBirth required";
		res = false;
	}
	if(email.trim().length == 0){
		document.getElementById("emailError").innerHTML = "email required";
		res = false;
	}
	if(phone.trim().length == 0){
		document.getElementById("phoneError").innerHTML = "phone required";
		res = false;
	}
	if(country.trim().length == 0){
		document.getElementById("countryError").innerHTML = "country required";
		res = false;
	}
	if(pincode.trim().length == 0){
		document.getElementById("pincodeError").innerHTML = "pincode required";
		res = false;
	}
	if(street.trim().length == 0){
		document.getElementById("streetError").innerHTML = "street required";
		res = false;
	}
	if(doorNo.trim().length == 0){
		document.getElementById("doorNoError").innerHTML = "doorNo required";
		res = false;
	}
	return res;
}

function clearthis(element){
	element.nextElementSibling.nextElementSibling.innerHTML = "";
}

