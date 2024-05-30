<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="model.UserBean, java.util.Collection, java.util.Iterator"%>
<% if (session.getAttribute("registeredUser") == null) {
		response.sendRedirect("loginPage.jsp");
	}
	if (session.getAttribute("listaEmail") == null) {
		response.sendRedirect("UserControl?action=ottieni");
	}
%>
<jsp:useBean id="registeredUser" class="model.UserBean" scope="session"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
 	<title>Geek Factory - Admin</title>
    <link rel="stylesheet" href="./css/account.css">
    <link rel="icon" href="./img/icon.png">
</head>
<body>
	<div class="header">
		<jsp:include page="header.jsp"/>
	</div>
	
	<%
		Collection<String> lista = (Collection<String>) session.getAttribute("listaEmail");
	%>
	
	<div class="test">
	<div class="container" style="height: 400px">
		<div class="title">Admin hub</div>
		<div class="content" style="align: center">
		<form action="AdminControl" METHOD="POST">
			<div class="user-details">
				<div class="input-box">
					<span class="details">Seleziona email di un utente</span>
  					<select id="email" name="email" required>
  						<%	
  							if (lista != null && lista.size() != 0) {
  							Iterator<String> it = lista.iterator();
  							while (it.hasNext()) {
  								String email = (String) it.next();
  						%>
   						<option value="<%=email%>"><%=email%></option>
    					<%  }
  						   }
  						%>
  					</select>
				</div>
			</div>
			<div class="button">
				<input type="submit" name="ordini" value="Visualizza ordini cliente" style="margin-bottom: 40px">
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