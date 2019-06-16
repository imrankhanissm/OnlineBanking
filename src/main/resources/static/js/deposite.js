function validate(){
	var amount = Number(document.getElementById("amount").value);
	if(amount > 0){
		return true;
	}
	document.getElementById("amountError").innerHTML = "Invalid amount";
	return false;
}

function clearThis(element){
	element.nextElementSibling.innerHTML = "";	
}