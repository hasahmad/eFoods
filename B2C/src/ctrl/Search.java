package ctrl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.catalog.Item;
import model.catalog.Order;
import model.Model;

/**
 * Servlet implementation class Search
 */
@WebServlet("/Search")
public class Search extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Search() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession s = request.getSession();
		String query = request.getParameter("query");
		
		if (query != null) {
			try {
				Model m = Model.getInstance();
				List<Item> items = m.getFoodsByMultiple(query);
				request.setAttribute("searchItems", items);
			} catch (Exception e) {
				request.setAttribute("error", e.getMessage());
			}
		}
		
//		if (request.getAttribute("searchItems") == null) {
//			request.setAttribute("searchItems", new ArrayList<Item>());
//		}
		request.setAttribute("query", query);
		request.getRequestDispatcher("/WEB-INF/pages/Search.jspx").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
