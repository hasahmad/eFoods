package ctrl.api;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import model.Model;
import model.Order;
import model.catalog.Item;

/**
 * Servlet implementation class CartApi
 */
@WebServlet("/Api/Cart")
public class CartApi extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CartApi() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		response.setCharacterEncoding("utf-8");
		response.addHeader("Access-Control-Allow-Origin", "*");

		PrintWriter out = response.getWriter();
		JsonObject json = new JsonObject();
		JsonElement resultJson;
		Model model = Model.getInstance();
		HttpSession session = request.getSession();

		Order userOrder = (Order) session.getAttribute("order");
		List<Item> products = userOrder.getItems();

		String addToCart = request.getParameter("addToCart");
		String removeItem = request.getParameter("removeItem");
		String updateQty = request.getParameter("updateQty");
		String productQty = request.getParameter("qty");

		json.addProperty("status", 1);

		if (addToCart != null) {
			addItemToCart(model, products, json, addToCart);
		}

		if (updateQty != null) {
			updateQty(products, json, updateQty, productQty);
		}
		
		if (removeItem != null) {
			removeItem(products, json, removeItem);
		}

		resultJson = new Gson().toJsonTree(userOrder);
		if (!json.has("data")) {
			json.add("data", resultJson);
		}

		if (!json.has("action")) {
			json.addProperty("action", "GET");
		}		

		out.print(json.toString());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	private void addItemToCart(Model model, List<Item> products, JsonObject json, String addToCart) {
		try {
			Item product = model.getFoodBy("num", addToCart);
			if (products.size() > 0) {
				for (Item p: products) {
					if (!p.getNumber().equals(addToCart)) {	
						product.setQuantity(1);
						products.add(product);
						json.addProperty("status", 1);
						
						json.addProperty("action", "ADD");
						json.addProperty("actionReq", addToCart);
					}
				}
			} else {
				product.setQuantity(1);
				products.add(product);
				json.addProperty("status", 1);

				json.addProperty("action", "ADD");
				json.addProperty("actionReq", addToCart);
			}
		} catch (Exception e) {
			json.addProperty("status", 0);
			json.addProperty("data", e.getMessage());
		}
	}
	
	
	private void updateQty(List<Item> products, JsonObject json, String updateQty, String productQty) {
		try {
			Item product;
			for (Item it: products) {
				if (it.getNumber().equals(updateQty)) {
					if (productQty != null) {
						if (productQty.equals("0")) {
							products.remove(it);
						} else {
							product = it;
							product.setQuantity(productQty);
						}
						json.addProperty("status", 1);
					} else {
						json.addProperty("status", 0);
						json.addProperty("data", "No Quantity Provided.");
					}
					json.addProperty("action", "UPDATE");
					json.addProperty("actionReq", updateQty);
				}
			}
		} catch (Exception e) {
			json.addProperty("status", 0);
			json.addProperty("data", e.getMessage());
		}
	}
	
	private void removeItem(List<Item> products, JsonObject json, String removeItem) {
		try {
			for (Item it: products) {
				if (it.getNumber().equals(removeItem)) {
					products.remove(it);

					json.addProperty("status", 1);
					json.addProperty("action", "REMOVE");
					json.addProperty("actionReq", removeItem);
				}
			}
		} catch (Exception e) {
			json.addProperty("status", 0);
			json.addProperty("data", e.getMessage());
		}
	}

}
