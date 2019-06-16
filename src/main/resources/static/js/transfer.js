function validate(){
	var accountNumber = document.getElementById("accountNumber").value;
	var amount = Number(document.getElementById("amount").value);
	var res = true;
	if(accountNumber.length < 8){
		document.getElementById("accountNumberError").innerHTML = "Invalid account number"
		res = false;
	}
	if(amount <= 0){
		document.getElementById("amountError").innerHTML = "Invalid amount";
		res = false;
	}
	return res;
}

function clearThis(element){
	element.nextElementSibling.innerHTML = "";	
}