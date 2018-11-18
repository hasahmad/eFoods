package ctrl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Date;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import analytics.ComputeAnalytics;
import model.Account;
import model.Order;
import model.Orders;
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
		Account user = (Account) s.getAttribute("AUTH");
		Order order = (Order) s.getAttribute("order");

		String userName = request.getParameter("name");
		String username = request.getParameter("user");
		String userHash = request.getParameter("hash");
		
		response.setContentType("text/xml");
		Writer out = response.getWriter();
		StringWriter sw = new StringWriter();
		sw.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>");

		if (userName != null && username != null) {
			user.setName(userName);
			user.setUsername(username);
		}

		ServletContext context = getServletContext();
		String poPath = context.getRealPath("/POs");

		if (!Utils.isValidUser(user)) {
			String redirectUrl = "https://www.eecs.yorku.ca/~roumani/servers/auth/oauth.cgi";
			String params = "?back=" + request.getRequestURL();
			response.sendRedirect(redirectUrl + params);
		} else {
			if(order.getItems().size() > 0) {
				order.setCustomer(user);
				order.setSubmitted(new Date());
				order.setId(((Integer) request.getAttribute("totalAllOrders")));

				try {
					Orders orders = Orders.getInstance();
					OrderDAO orderDao = new OrderDAO(new File(poPath));
					sw = orders.createPO(poPath, 
							orderDao.getOrderFileName(user.getUsername(), ((Integer) s.getAttribute("totalOrders")) + 1), 
							"res/xml/PO.xsl", orderDao, order);
					order.clearCart();
				} catch (Exception e) {
					sw.write("<error>" + e.getMessage() + "</error>\n");
					System.out.println("ERROR: " + e.getMessage());
				}
			} else {
				sw.write("<msg>" + "Empty Cart" + "</msg>\n");
				System.out.println("Empty Cart!");
			}
		}
		
		out.write(sw.toString());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
