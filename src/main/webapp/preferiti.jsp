<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="model.ProductBean, model.UserBean, model.CartBean, java.util.*, javax.servlet.RequestDispatcher, java.text.DecimalFormat"%>
<!DOCTYPE html>
<%	Collection<?> preferiti = (Collection<?>) request.getSession().getAttribute("preferiti");
	if(preferiti == null) {
		response.sendRedirect("./AggiungiPreferiti?opzione=lista");	
		return;
	}
%>
<html>
<head>
<meta charset="ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" href="css/productlist.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/css/all.min.css" integrity="sha512-KfkfwYDsLkIlwQp6LFnl8zNdLGxu9YAA1QvwINks4PhcElQSvqcyVLLD9aMhXd13uQjoXtEKNosOWaZqXgel0g==" crossorigin="anonymous" referrerpolicy="no-referrer" />
<link rel="icon" href="./img/icon.png">
<title>Geek Factory - Preferiti</title>
</head>
<body>
	
	<jsp:include page="header.jsp"/>
	
	<div class = "wrapper" style="margin-bottom: 200px">
		<div class="container">
		
			<%  if (!preferiti.isEmpty()) {
			%>
			<div class = "heading-title" style="margin-top: 75px">
				<h1>Preferiti</h1>
			</div>
			<%	}
				else {
			%>
			<div class = "heading-title" style="margin-top: 75px">
				<h1>Preferiti</h1>
			</div>
			<div class = "heading-title" style="margin-top: 15px; margin-bottom: 250px">
				<h3>Nessun prodotto nei preferiti</h3>
			</div>
			<%	}
			%>
			
			<div class = "item-grid" style="margin-top: 20px">
				
				<%
				if (preferiti != null && preferiti.size() != 0) {
					Iterator<?> it = preferiti.iterator();
					while (it.hasNext()) {
						ProductBean bean = (ProductBean) it.next();
						Double tot = bean.getPrezzo() + bean.getSpedizione();
						DecimalFormat df = new DecimalFormat("0.00");
						
						String prezzoTot = df.format(tot) + " ";	
						String image = "img/productIMG/" + bean.getImmagine();
				%>
				
				<div class="item">
					<div class = "item-img">
						<img src = "<%=image%>" alt = "">
						
						<div class = "item-action">
							<a href = "productDetail.jsp?codice=<%=bean.getCodice()%>" class = "view">
								<span>
									<i class = "fas fa-search-plus"></i>
								</span>
							</a>
							<a href = "CartControl?action=rimuoverePref&codice=<%=bean.getCodice()%>" class = "wishlist">
								<span>
									<i class = "fas fa-trash-alt"></i>
								</span>
							</a>
						</div>
					</div>
					
					
					<div class = "item-details">
						<p class = "name" style="color: #fff"><%=bean.getNome()%></p>
						<p class = "price" style="color: #fff"><%=prezzoTot%>&euro;</p>
					</div>
				</div>
				<% 
						} 
					}
				%>
				
			</div>
		</div>
	</div>
	
	
	
	<jsp:include page="footer.jsp"/>
	
	<script>
        let view = document.querySelectorAll('.view');
        let wishlist = document.querySelectorAll('.wishlist');

        setContent(view, 'Dettagli');
        setContent(wishlist, 'Rimuovi dai preferiti');

        function setContent(list, text){
            list.forEach((listItem) => {
                listItem.setAttribute('data-before', text);
            });
        }
    </script>
</body>
</html>