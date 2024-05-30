package control;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.CartBean;
import model.OrderBean;
import model.OrderModel;
import model.UserBean;

/**
 * Servlet implementation class OrderControl
 */
@WebServlet("/OrderControl")
public class OrderControl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public OrderControl() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		OrderModel model = new OrderModel();
		String email = (String) request.getSession().getAttribute("email");
		
		if (request.getParameter("action") != null && request.getParameter("action").compareTo("ottieni") == 0) {
			Collection<OrderBean> listaOrdini;
			try {
				listaOrdini = model.getOrders((String) request.getSession().getAttribute("email"));
				listaOrdini = model.getRecensioni(listaOrdini, email);
				request.getSession().setAttribute("listaOrdini", listaOrdini);
				request.getSession().setAttribute("ControlOrd", false);
			} 
			catch (SQLException e) {
				e.printStackTrace();
			}
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/ordini.jsp");
			dispatcher.forward(request, response); 
		}
		else if (request.getParameter("action") != null && request.getParameter("action").compareTo("recensione") == 0) {
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/recensione.jsp?codice=" + request.getParameter("codice"));
			dispatcher.forward(request, response);
		}
		else if (request.getParameter("action") != null && request.getParameter("action").compareTo("aggiungi") == 0) {
			Collection<OrderBean> lista = model.addRecensione(Integer.parseInt(request.getParameter("codice")), (String) request.getSession().getAttribute("email"), Integer.parseInt(request.getParameter("votazione")), (String) request.getParameter("testo"), (Collection<OrderBean>) request.getSession().getAttribute("listaOrdini"));
			request.getSession().setAttribute("listaOrdini", lista);
			
			request.getSession().setAttribute("refreshProduct", true);
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/ordini.jsp");
			dispatcher.forward(request, response);
		}
		else {
			HttpSession session = request.getSession();
			String redirectedPage = "/index.jsp";
			
			if (session.getAttribute("carrello") != null && session.getAttribute("registeredUser") != null) {
				CartBean cart = model.doOrder((CartBean) session.getAttribute("carrello"), (UserBean) session.getAttribute("registeredUser"));

				if (cart != null) {
					request.getSession().setAttribute("carrello", cart);
				}
				Collection<OrderBean> listaOrdini;
				try {
					listaOrdini = model.addOrdini((CartBean) session.getAttribute("carrello"), (String) request.getSession().getAttribute("email"), (Collection<OrderBean>) session.getAttribute("listaOrdini"));
					listaOrdini = model.getRecensioni(listaOrdini, email);
					request.getSession().setAttribute("listaOrdini", listaOrdini);
				}
				catch (SQLException e) {
					
				}
				request.getSession().setAttribute("ControlOrd", true);
				response.sendRedirect(request.getContextPath() + redirectedPage);
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
