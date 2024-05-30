<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="model.UserBean"%>
<% if (session.getAttribute("registeredUser") == null) {
		response.sendRedirect("loginPage.jsp");
	}
%>
<jsp:useBean id="registeredUser" class="model.UserBean" scope="session"/>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
 	<title>Geek Factory - Account</title>
    <link rel="stylesheet" href="./css/account.css">
</head>
<body>
	<div class="header">
		<jsp:include page="header.jsp"/>
	</div>
	
	<div class="test">
	<div class="container">
		<div class="title">Informazioni Account</div>
		<div class="content">
		<form action="AccountSettings" METHOD="POST">
			<div class="user-details">
				<div class="input-box">
					<span class="details">E-mail</span>
					<input type="email" name="email" pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,}$" title="Email non valida" maxlength="50" value="<jsp:getProperty name = "registeredUser" property = "email"/>">
				</div>
				<div class="input-box">
					<span class="details">Nome</span>
					<input type="text" name="nome" maxlength="50" value="<jsp:getProperty name = "registeredUser" property = "nome"/>">
				</div>
				<div class="input-box">
					<span class="details">Cognome</span>
					<input type="text" name="cognome" maxlength="50" value="<jsp:getProperty name = "registeredUser" property = "cognome"/>">
				</div>
				<div class="input-box">
					<span class="details">Indirizzo</span>
					<input type="text" name="indirizzo" maxlength="50" value="<jsp:getProperty name = "registeredUser" property = "indirizzo"/>">
				</div>
				<div class="input-box">
					<span class="details">Numero di telefono</span>
					<input type="text" name="telefono" pattern="[0-9]{10,15}" title="Inserisci un numero di telefono di 10-15 cifre" value="<jsp:getProperty name = "registeredUser" property = "telefono"/>">
				</div>
				<div class="input-box">
					<span class="details">Numero carta di credito</span>
					<input type="text" name="carta" pattern="[0-9]{16}" title="Inserisci un numero valido di 16 cifre" value="<jsp:getProperty name = "registeredUser" property = "numero"/>">
				</div>
				<div class="input-box">
					<span class="details">Intestatario</span>
					<input type="text" name="intestatario" maxlength="50" value="<jsp:getProperty name = "registeredUser" property = "intestatario"/>">
				</div>
				<div class="input-box">
					<span class="details">CVV</span>
					<input type="text" name="cvv" pattern="[0-9]{3}" title="Inserisci un numero di 3 cifre" value="<jsp:getProperty name = "registeredUser" property = "cvv"/>">
				</div>
			</div>
			<div class="button">
				<input type="submit" name="salva" value="Salva informazioni" style="margin-bottom: 40px">
				<input type="submit" class="ordini" name="ordini" value="Lista ordini">
				<input type="submit" class="vendi" name="vendi" value="Vendi un prodotto" style="margin-bottom: 40px">
				<input type="submit" name="logout" value="Logout">
			</div>
		</form>
		
	</div>
	</div>
	</div>
	
	<div class="footer">
		<jsp:include page="footer.jsp"/>
	</div>	
</body>

</html>