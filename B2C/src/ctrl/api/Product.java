package ctrl.api;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import model.Model;
import model.catalog.Category;
import model.catalog.Item;

/**
 * Servlet implementation class Product
 */
@WebServlet("/Api/Product")
public class Product extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Product() {
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

		String num = request.getParameter("num");
		String cid = request.getParameter("cid");
		String limit = request.getParameter("limit");
		String byCat = request.getParameter("byCat");
		
		try {			
			if (byCat != null) {
				if (byCat.toLowerCase().equals("name")) {
					Map<String, List<Item>> result = model.getCatNameWithFoods();
					resultJson = new Gson().toJsonTree(result);
				} else {
					Map<Category, List<Item>> result = model.getCatsWithFoods();
					
					resultJson = new Gson().toJsonTree(result);
				}
			} else {
				List<Item> result = model.getFoods();

				if (num != null) 
				{
					result = model.getFoodsBy("num", num, false);
				}
				else if (cid != null) 
				{
					result = model.getFoodsBy("cid", cid, false);
				}
				
				if (limit != null) {
					int l = Integer.parseInt(limit);
					result = result.subList(0, l);
				}
				resultJson = new Gson().toJsonTree(result);
			}

			json.add("data", resultJson);
			json.addProperty("status", 1);
		} catch (Exception e) {
			json.addProperty("status", 0);
			json.addProperty("data", e.getMessage());
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

}
