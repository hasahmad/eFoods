package ctrl;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import model.Account;
import model.dao.OrderDAO;

/**
 * Servlet implementation class PurchaseOrders
 */
@WebServlet("/PurchaseOrders")
public class PurchaseOrders extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PurchaseOrders() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		response.setContentType("text/xml");
		
		String poPath = getServletContext().getRealPath("/POs");
		String xslFilePath = getServletContext().getRealPath("res/xml/PO.xsl");

		HttpSession s = request.getSession();
		Writer out = response.getWriter();
		StringWriter sw = null;
		
		String orderNum = request.getParameter("orderNum");	
		Account user = (Account) s.getAttribute("AUTH");

		OrderDAO orderDao = new OrderDAO(new File(poPath));
		File[] orders = orderDao.getPOs(user.getUsername());
		File orderFile = null;

//		String[] userOrderLinks = new String[orders.length + 1];
		Map<String, String> userOrderLinks = new HashMap<>();

		try {
			if (orders.length > 0) {
				int i=1;
				for (File f: orders) {
					String fileName = f.getAbsolutePath().replace(poPath, "");
					fileName = fileName.replaceAll("/", "");
					fileName = fileName.replaceAll(".xml", "");
					fileName = fileName.replace(orderDao.getOrderFileNameFirstPart(user.getUsername()), "");
					userOrderLinks.put(fileName + "", request.getContextPath() + "/PurchaseOrders?orderNum=" + fileName);

					if (orderNum != null && fileName.equals(orderNum)) {
						orderFile = f;
					}

					i++;
				}
				
				if (orderFile != null) {
					sw = new StringWriter();
					Source xml = new StreamSource(orderFile);
					Source xslt = new StreamSource(xslFilePath);
					
					TransformerFactory tFactory = TransformerFactory.newInstance();
					Transformer trasform = tFactory.newTransformer(xslt);
					trasform.transform(xml, new StreamResult(sw));
				} else {
					if (orderNum == null) {
						s.setAttribute("ordersLinks", userOrderLinks);
						request.setAttribute("ordersLinks", userOrderLinks);
					} else {
						request.setAttribute("error", "No Purchase Order found!");
					}
				}
			} else {
				request.setAttribute("error", "No Purchase Order for current user!");
			}
			
		} catch(Exception e) {
			request.setAttribute("error", e.getMessage());
		}
		
		if (sw != null) {
			out.write(sw.toString());
		} else {
			request.getRequestDispatcher("/WEB-INF/pages/PurchaseOrders.jspx").forward(request, response);
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
