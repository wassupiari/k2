<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="model.UserBean"%>
<% if (session.getAttribute("registeredUser") != null) {
		UserBean user = (UserBean) session.getAttribute("registeredUser");
		if(user.getEmail().compareTo("") != 0) {
			response.sendRedirect("index.jsp");
		}
	}
%>
<jsp:useBean id="registeredUser" class="model.UserBean" scope="session"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" href="./css/loginpage.css">
<link rel="icon" href="./img/icon.png">
<title>Geek Factory - Login</title>	

</head>
<body>

<div class ="container">
	<div class ="forms-container">
	
	
		<div class = "signin-signup">
			<form action ="Login" METHOD="POST" class="signi-in-form">
			
			<h2 class="title">Hai già un account?</h2>

				<div class="input-field">
					<i class="fas fa-user"></i>
					<input type="email" NAME="j_email" maxlength="50" placeholder="e-mail" required autofocus/>
				</div>
				
				<div class="input-field">
					<i class="fas fa-lock"></i>
					<input type="password" NAME="j_password" maxlength="50" placeholder="password" required/> 
				</div>
				
				<div>
					<% if(session.getAttribute("login-error") != null) { %>
					<h4 style="color: red">Password e/o e-mail sbagliate o inesistenti. Riprova.</h4>
					<% } %>
				</div>
				
				
				<input type="submit" value="Accedi" class="btn solid"/>
			</form>
		</div>
		
		
	</div>
</div>
	
	<div class="panels-container">
	<div class="panel left-panel">
          <div class="content">
            <h3 style="margin-bottom: 20px">Crea il tuo account</h3>
            <button class="btn transparent" id="sign-up-btn" onclick="location.href = 'register-form.jsp';">
              Registrati
            </button>
          </div>
  
        </div>
          
        </div>
      </div>
    </div>
    
     
</body>
</html>