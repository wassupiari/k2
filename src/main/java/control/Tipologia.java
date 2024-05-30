package control;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Tag
 */
@WebServlet("/Tipologia")
public class Tipologia extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Tipologia() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		if (request.getParameter("tipologia").compareTo("Action-Figures") == 0) {
			request.getSession().setAttribute("tipologia", "Action Figures");
		}
		else if (request.getParameter("tipologia").compareTo("Arredamento-Casa") == 0) {
			request.getSession().setAttribute("tipologia", "Arredamento Casa");
		}
		else if (request.getParameter("tipologia").compareTo("Gadget") == 0) {
			request.getSession().setAttribute("tipologia", "Gadget");
		}
		
		response.sendRedirect(request.getContextPath() + "/ProductsPage.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
