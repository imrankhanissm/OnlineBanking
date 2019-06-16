function validate(){
	var res = true;
	var username = document.getElementById("usernamelogin").value;
	var password = document.getElementById("passwordlogin").value;
	if(username.trim().length == 0){
		document.getElementById("usernameError").innerHTML = "username required";
		res = false;
	}
	if(password.trim().length == 0){
		document.getElementById("passwordError").innerHTML = "password required";
		res = false;
	}
	return res	;
}

function clearthis(element){
	element.nextElementSibling.nextElementSibling.innerHTML = "";
}