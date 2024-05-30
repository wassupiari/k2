<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
 	<title>Geek Factory - Registrazione</title>
    <link rel="stylesheet" href="./css/register.css">
    <link rel="icon" href="./img/icon.png">
</head>

<body>

	<div class="container">
		<div class="title">Registrazione</div>
		<div class="content">
		<form action="Register" METHOD="POST" name="invio">
			<div class="user-details">
				<div class="input-box">
					<span class="details">E-mail</span>
					<input type="email" name="email" pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,}$" title="Email non valida" maxlength="50" placeholder="Inserisci la tua email" autofocus required>
				</div>
				<div class="input-box">
					<span class="details">Password</span>
					<input type="password" name="password" pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,50}" title="La password deve contenere almeno una lettera maiuscola, una minuscola e un numero, e deve essere almeno di 8 caratteri" placeholder="Inserisci la tua password" required>
				</div>
				<div class="input-box">
					<span class="details">Nome</span>
					<input type="text" name="nome" maxlength="50" placeholder="Inserisci il tuo nome" required>
				</div>
				<div class="input-box">
					<span class="details">Cognome</span>
					<input type="text" name="cognome" maxlength="50" placeholder="Inserisci il tuo cognome" required>
				</div>
				<div class="input-box">
					<span class="details">Indirizzo</span>
					<input type="text" name="indirizzo" maxlength="50" placeholder="Inserisci il tuo indirizzo" required>
				</div>
				<div class="input-box">
					<span class="details">Numero di telefono</span>
					<input type="text" name="telefono" pattern="[0-9]{10,15}" title="Inserisci un numero di telefono di 10-15 cifre" placeholder="Inserisci il tuo numero di telefono" required>
				</div>
				<div class="input-box">
					<span class="details">Numero carta di credito</span>
					<input type="text" name="carta" pattern="[0-9]{16}" title="Inserisci un numero valido di 16 cifre" placeholder="Inserisci il numero della carta" required>
				</div>
				<div class="input-box">
					<span class="details">Intestatario</span>
					<input type="text" name="intestatario" maxlength="50" placeholder="Inserisci l'intestatario della carta" required>
				</div>
				<div class="input-box">
					<span class="details">CVV</span>
					<input type="text" name="cvv" pattern="[0-9]{3}" title="Inserisci un numero di 3 cifre" placeholder="Inserisci il CVV della carta" required>
				</div>
			</div>
			<div>
				<% if(session.getAttribute("register-error") != null) { 
				   Boolean regErr = (Boolean) session.getAttribute("register-error");
				   if (regErr == true) {
				%>
				<h4 id="error-message-p"></h4>
				<% } 
				  }
				%>
			</div>
			<div class="button">
					<input type="submit" value="Registrati">
			</div>
		</form>
	</div>
	</div>
	<script src="script/jquery.js"></script>
	<script>
		$(document).ready(function(){
			var msg = "<h4>Questa e-mail è stata già utilizzata!</h4>"
			$("#error-message-p").append(msg);
			$("#error-message-p").css("color", "red");
		});
	</script>
</body>
</html>