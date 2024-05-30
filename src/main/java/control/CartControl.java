package control;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.CartBean;
import model.CartModel;
import model.ProductBean;
import model.UserBean;

/**
 * Servlet implementation class CartControl
 */
@WebServlet("/CartControl")
public class CartControl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CartControl() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (request.getParameter("action") != null && request.getParameter("action").compareTo("diminuisci") == 0) {
			String codiceStr = request.getParameter("codice");
			int codice = Integer.parseInt(codiceStr);
			CartBean carrello = (CartBean) request.getSession().getAttribute("carrello");
			carrello.retriveByKey(codice).decreaseQuantity();
			if (carrello.retriveByKey(codice).getQuantity() == 0) {
				carrello.removeItem(codice);
			}
			request.getSession().setAttribute("carrello", carrello);
			request.getRequestDispatcher("/cart.jsp").forward(request, response);
		}
		else if (request.getParameter("action") != null && request.getParameter("action").compareTo("aumenta") == 0) {
			String codiceStr = request.getParameter("codice");
			int codice = Integer.parseInt(codiceStr);
			CartBean carrello = (CartBean) request.getSession().getAttribute("carrello");
			if (carrello.retriveByKey(codice).getQuantity() != 99) {
				carrello.retriveByKey(codice).addQuantity();
			}
			request.getSession().setAttribute("carrello", carrello);
			request.getRequestDispatcher("/cart.jsp").forward(request, response);
		}
		else if (request.getParameter("action") != null && request.getParameter("action").compareTo("rimuoverePref") == 0) {
			String codiceProd = request.getParameter("codice");
			int codiceProdotto = Integer.parseInt(codiceProd);
		
			@SuppressWarnings("unchecked")
			Collection<ProductBean> preferitiList = (Collection<ProductBean>) request.getSession().getAttribute("preferiti");
			if (!preferitiList.isEmpty()) {
				preferitiList.removeIf(a -> a.getCodice() == codiceProdotto);
			}
			request.getSession().setAttribute("preferiti", preferitiList);
			request.getRequestDispatcher("/preferiti.jsp").forward(request, response);
		}
		else if (request.getParameter("action") != null && request.getParameter("action").compareTo("rimuovere") == 0) {
			String codiceProd = request.getParameter("codice");
			int codiceProdotto = Integer.parseInt(codiceProd);
		
			CartBean carrello = (CartBean) request.getSession().getAttribute("carrello");
			if (!carrello.isEmpty()) {
				carrello.removeItem(codiceProdotto);
			}
			request.getSession().setAttribute("carrello", carrello);
			request.getRequestDispatcher("/cart.jsp").forward(request, response);
		}
		else if (request.getParameter("action") != null && request.getParameter("action").compareTo("aggiungi") == 0) {
			CartBean carrello = (CartBean) request.getSession().getAttribute("carrello");
			String codiceProd = request.getParameter("codice");
			int codiceProdotto = Integer.parseInt(codiceProd);
			CartModel model = new CartModel();
			
			carrello = model.aggiungiAlCarrello(carrello, codiceProdotto);
			
			request.getSession().setAttribute("carrello", carrello);
			request.getRequestDispatcher("/ProductsPage.jsp").forward(request, response);
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
