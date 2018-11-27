package ctrl;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Account;
import model.Model;
import model.helpers.Utils;

/**
 * Servlet implementation class Admin
 */
@WebServlet("/Admin")
public class Admin extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Admin() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Account user = (Account) request.getSession().getAttribute("AUTH");

		if (Utils.isValidUser(user)) {
			request.setAttribute("addToCartAvgCount", request.getSession().getAttribute("addToCartAvgCount"));
			request.setAttribute("addToCartAvgTime", request.getSession().getAttribute("addToCartAvgTime"));
			
			request.setAttribute("checkoutAvgTime", request.getSession().getAttribute("checkoutAvgTime"));
			request.setAttribute("checkoutAvgCount", request.getSession().getAttribute("checkoutAvgCount"));
		} else {
			request.setAttribute("error", "Invalid User!");
		}

		request.getRequestDispatcher("/WEB-INF/pages/Admin.jspx").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
