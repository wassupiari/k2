package control;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.OrderBean;
import model.OrderModel;
import model.ProductModel;
import model.RecensioneBean;

/**
 * Servlet implementation class RecensioneControl
 */
@WebServlet("/RecensioneControl")
public class RecensioneControl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RecensioneControl() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (request.getParameter("action") != null && request.getParameter("action").compareTo("modifica") == 0) {
			int codice = Integer.parseInt(request.getParameter("codice"));
			ProductModel model = new ProductModel();
			String email = request.getParameter("email");
			
			RecensioneBean bean = model.getRecensione(codice, email);
			
			String redirectedPage = "/recensione.jsp?voto=" + bean.getVoto() + "&testo=" + bean.getTesto() + "&emailRec=" + email;
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(redirectedPage);
			dispatcher.forward(request, response);
		}
		else if (request.getParameter("action") != null && request.getParameter("action").compareTo("aggiungiMod") == 0) {
			String email = request.getParameter("emailRec");
			int codice = Integer.parseInt(request.getParameter("codice"));
			Collection<OrderBean> ordini = null;
			if (request.getSession().getAttribute("listaOrdini") != null) {
				ordini = (Collection<OrderBean>) (request.getSession().getAttribute("listaOrdini"));
			}
			OrderModel model = new OrderModel();
			RecensioneBean bean = new RecensioneBean();
			bean.setCodiceProdotto(codice);
			bean.setVoto(Integer.parseInt(request.getParameter("votazione")));
			bean.setTesto(request.getParameter("testo"));
			
			ordini = model.addSingleRecensione(email, bean, ordini);
			if (ordini != null) {
				request.getSession().setAttribute("listaOrdini", ordini);
			}
			
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/controllo-ordini.jsp?email=" + email);
			dispatcher.forward(request, response);
		}
		else if (request.getParameter("action") != null && request.getParameter("action").compareTo("elimina") == 0) {
			String email = request.getParameter("email");
			int codice = Integer.parseInt(request.getParameter("codice"));
			OrderModel model = new OrderModel();
			Collection<OrderBean> ordini = null;
			if (request.getSession().getAttribute("listaOrdini") != null) {
				ordini = (Collection<OrderBean>) (request.getSession().getAttribute("listaOrdini"));
			}
			
			ordini = model.eliminaSingleRecensione(email, codice, ordini);
			if (ordini != null) {
				request.getSession().setAttribute("listaOrdini", ordini);
			}
			
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/controllo-ordini.jsp?email=" + email);
			dispatcher.forward(request, response);
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
