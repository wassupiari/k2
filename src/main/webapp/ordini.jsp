<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="model.OrderBean, model.ProductBean, java.text.DecimalFormat, java.util.*"%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="css/cart-style.css"/>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/css/all.min.css" integrity="sha512-KfkfwYDsLkIlwQp6LFnl8zNdLGxu9YAA1QvwINks4PhcElQSvqcyVLLD9aMhXd13uQjoXtEKNosOWaZqXgel0g==" crossorigin="anonymous" referrerpolicy="no-referrer" />
<link rel="icon" href="./img/icon.png">
<title>Geek Factory - Ordini</title>
</head>
<body>
<jsp:include page="header.jsp"/>

<section id="cart-container">
<div class="small-container cart-page" style="margin-bottom: 480px; margin-top: 110px">
<h1 style="margin-bottom: 30px; margin-left: 30px; font-family: Arial, sans-serif; color: white">Lista Ordini</h1> 
	<table>
		<tr>
			<th>Codice ordine</th>
			<th>Immagine</th>
			<th>Nome</th>
			<th>Prezzo totale</th>
			<th>Quantità</th>
			<th>Data acquisto</th>
			<th>Email venditore</th>
			<th>Recensione</th>
			<th></th>
		</tr>
		<%
		Collection<OrderBean> ordini = null;
		DecimalFormat df = new DecimalFormat("0.00");
		
		
		/*if (session.getAttribute("ControlOrd") != null) {
			Boolean control = (Boolean) session.getAttribute("ControlOrd");
			if (control == true) {
				response.sendRedirect("OrderControl?action=ottieni");
			}
		}
		*/
		ordini = (Collection<OrderBean>) session.getAttribute("listaOrdini");

		if (ordini != null && ordini.size() != 0) {
			Iterator<?> it = ordini.iterator();
			while (it.hasNext()) {
				OrderBean ord = (OrderBean) it.next();
				ProductBean prod = ord.getProdotto();
			
				Double tot = prod.getPrezzo();
				String prezzoTot = df.format(tot);
			
				String image = "img/productIMG/" + prod.getImmagine();
		%>
		<tr>
			<td><%=ord.getCodice()%></td>
			<td>
				<div class="order-info">
					<img src="<%=image%>" style="width: 120px; height: 120px">
				</div>
			</td>
			<td><%=prod.getNome()%></td>
			<td id="price"><%=tot%> &euro;</td>
			<td id="quantity"><%=prod.getQuantity()%></td>
			<td><%=ord.getData() %></td>
			<td class="product-price" id="subtotal"><%=ord.getVenditore()%></td>
			<% if (prod.getVotazione() >= 1) {
			%>
			<td><ul class = "rating">
				<%
				double votazione = prod.getVotazione();
				int count = 0;
				
				if (votazione >= 1) {
				while (votazione >= 1) {
					if (votazione >= 2) {
						votazione = votazione - 2;
						count += 1;
				%>
				<li><i class = "fas fa-star" style="color: #ece50c"></i></li>
				<%  } 
					else if (votazione >= 1) {
						votazione = votazione - 1;
						count += 1;
				%>
				<li><i class = "fas fa-star-half-alt" style="color: #ece50c"></i></li>
				<% }
				}
				}
				else {
					count += 1;
				%>
				<li><i class = "far fa-star" style="color: #ece50c"></i></li>
				<%
				}
				while (count != 5) {
					count++;
				%>
				<li><i class = "far fa-star" style="color: #ece50c"></i></li>
				<%
				}
				%>
				</ul></td>
			<% }
			else {
			%>
			<td><a href="OrderControl?action=recensione&codice=<%=prod.getCodice()%>" class="button-rec" id="button-rec"><i class="far fa-edit"></i></a></td>
			<% } 
			%>
			<td></td>
			
		</tr>
		<% 	}
		}
		%>
	</table>
	</div>
</section>

<jsp:include page="footer.jsp"/>

</body>
</html>