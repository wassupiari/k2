package control;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Collection;
import java.util.LinkedList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.CartBean;
import model.DriverManagerConnectionPool;
import model.PreferitiModel;
import model.ProductBean;

/**
 * Servlet implementation class AggiungiPreferiti
 */
@WebServlet("/AggiungiPreferiti")
public class AggiungiPreferiti extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AggiungiPreferiti() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		if (request.getParameter("opzione") != null && request.getParameter("opzione").compareTo("lista") == 0) {
			String userEmail = (String) request.getSession().getAttribute("email");
			PreferitiModel model = new PreferitiModel();
			Collection<ProductBean> preferiti = model.ottieni(userEmail);
			
			request.getSession().setAttribute("preferiti", preferiti);
			
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/preferiti.jsp");
			dispatcher.forward(request, response);
		}
		else {	
			String userEmail = (String) request.getSession().getAttribute("email");
			String codiceProd = request.getParameter("codice");
			int codiceProdotto = Integer.parseInt(codiceProd);
			PreferitiModel model = new PreferitiModel();
			@SuppressWarnings("unchecked")
			Collection<ProductBean> preferiti = (Collection<ProductBean>) request.getSession().getAttribute("preferiti");
		
			preferiti = model.inserisci(userEmail, codiceProdotto, preferiti);
			request.getSession().setAttribute("preferiti", preferiti);
		
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
