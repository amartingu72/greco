function checkProfile(form) {
	
	//Confirmamos password,
	//var password = form[form.id + ":password"].value;
	//var passwordConfirm = form[form.id + ":passwordConfirm"].value;
	//Comfirmamos que la password coincide con su confirmaci�n.
	//if (password != passwordConfirm) {
	//	alert("La password y confirmaci�n no coinciden.");
	//	return false;
	//}	
	//Confirmamos que el email es correcto.
	var email = form[form.id + ":email"].value;
	var emailRegEx = /^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,4}$/i;
    if (email.search(emailRegEx) == -1) {
         alert("Email incorrecto.");
         return false;
    }

}