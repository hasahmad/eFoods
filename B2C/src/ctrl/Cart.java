package ctrl;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Model;
import model.Order;
import model.catalog.Item;

/**
 * Servlet implementation class Cart
 */
@WebServlet("/Cart")
public class Cart extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Cart() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String addToCart = request.getParameter("addToCart");
		String updateQty = request.getParameter("updateQty");
		String productQty = request.getParameter("productQty");
		String removeItem = request.getParameter("removeItem");

		String productNum = request.getParameter("productNum");

		Model model = Model.getInstance();
		HttpSession session = request.getSession();

		Order userOrder = (Order) session.getAttribute("order");
		List<Item> products = userOrder.getItems();

		if (addToCart != null) {
			if (productNum != null) {
				try {
					Item product = model.getFoodBy("num", productNum);
					if (products.size() > 0) {
						for (Item p: products) {
							if (!p.getNumber().equals(productNum)) {	
								product.setQuantity(1);
								products.add(product);
							} else {
								request.setAttribute("error", "Item is already in cart");
							}
						}
					} else {
						product.setQuantity(1);
						products.add(product);
					}
				} catch (Exception e) {
					request.setAttribute("error", e.getMessage());
				}
			}
		}
		
		if (updateQty != null) {
			System.out.println("updateQty");
			if (productNum != null) {
				
				try {
					Item product;
					for (Item it: products) {
						if (it.getNumber().equals(productNum)) {
							if (productQty != null) {
								System.out.println("updateQtyPNum: " + productNum + ", Qty: " + productQty);
								product = it;
								product.setQuantity(productQty);
							} else {
								request.setAttribute("error", "No Quantity Provided.");
							}
						}
					}
				} catch (Exception e) {
					request.setAttribute("error", e.getMessage());
				}
			}
		}
		
		if (removeItem != null) {
			if (productNum != null) {
				try {
					for (Item it: products) {
						if (it.getNumber().equals(productNum)) {
							products.remove(it);
						}
					}
				} catch (Exception e) {
					request.setAttribute("error", e.getMessage());
				}
			}
		}
		
		request.setAttribute("order", session.getAttribute("order"));
		System.out.println(((Order) session.getAttribute("order")).getItems().toString());

		request.getRequestDispatcher("/WEB-INF/pages/Cart.jspx").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
