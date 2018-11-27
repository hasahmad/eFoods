package ctrl;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Account;
import model.Model;
import model.catalog.Order;
import model.dao.OrderDAO;
import model.helpers.Utils;

/**
 * Servlet implementation class Checkout
 */
@WebServlet("/Checkout")
public class Checkout extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Checkout() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession s = request.getSession();
		Model m = Model.getInstance();
		Account user = (Account) s.getAttribute("AUTH");
		Order order = (Order) s.getAttribute("order");
		
		ServletContext context = getServletContext();
		String poPath = context.getRealPath("/POs");
		
		String confirm = request.getParameter("confirm");
//		String cancel = request.getParameter("cancel");
//		if (cancel != null) {
//			String redirectUrl = "Cart";
//			response.sendRedirect(redirectUrl);
//		}

		if (confirm != null) {
			if(order.getItems().size() > 0 && Utils.isValidUser(user)) {
				order.setCustomer(user);
				order.setSubmitted(new Date());
				order.setId(((Integer) request.getAttribute("totalAllOrders")));

				try {
					int orderNum = ((Integer) s.getAttribute("totalOrders")) + 1;
					m.createPO(poPath, orderNum, "res/xml/PO.xsl", order, user);

					order.clearCart();
					request.setAttribute("orderCreated", orderNum);
				} catch (Exception e) {
					request.setAttribute("error", e.getMessage());
					System.out.println("ERROR: " + e.getMessage());
				}
			} else {
				request.setAttribute("error", "Empty Cart!");
				System.out.println("Empty Cart!");
			}
		}

		request.setAttribute("order", s.getAttribute("order"));
		
		request.getRequestDispatcher("/WEB-INF/pages/Checkout.jspx").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
