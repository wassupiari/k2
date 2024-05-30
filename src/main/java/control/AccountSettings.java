package control;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.DriverManagerConnectionPool;
import model.UserBean;
import model.UserModel;

/**
 * Servlet implementation class AccountSettings
 */
@WebServlet("/AccountSettings")
public class AccountSettings extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AccountSettings() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		if (request.getParameter("salva") != null) {
			UserBean utente = new UserBean();
			utente.setEmail(request.getParameter("email"));
			utente.setNome(request.getParameter("nome"));
			utente.setCognome(request.getParameter("cognome"));
			utente.setIndirizzo(request.getParameter("indirizzo"));
			utente.setTelefono(request.getParameter("telefono"));
			utente.setNumero(request.getParameter("carta"));
			utente.setIntestatario(request.getParameter("intestatario"));
			utente.setCvv(request.getParameter("cvv"));
			String oldEmail = (String) request.getSession().getAttribute("email");
			
			UserModel model = new UserModel();
			UserBean utenteNuovo = model.insert(utente, oldEmail);
			
			request.getSession().setAttribute("registeredUser", utenteNuovo);
			request.getSession().setAttribute("email", utenteNuovo.getEmail());
			
			response.sendRedirect(request.getContextPath() + "/index.jsp");
		}
		else if (request.getParameter("logout") != null) {
			HttpSession session = request.getSession();  
            session.invalidate();
            
            response.sendRedirect(request.getContextPath() + "/index.jsp");
		}
		else if (request.getParameter("ordini") != null) {
			response.sendRedirect("OrderControl?action=ottieni");
				
		}
		else if (request.getParameter("vendi") != null) {
			response.sendRedirect(request.getContextPath() + "/vendita.jsp");
		}
	}

}
