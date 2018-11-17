package analytics;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import model.Order;

public class ComputeAnalytics {

	public static synchronized void computeOrders(HttpServletRequest req) {
		HttpSession s = req.getSession();
		Order order = (Order) s.getAttribute("order");
		order.computeAllCosts();
	}

}
