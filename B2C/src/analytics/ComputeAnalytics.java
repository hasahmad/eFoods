package analytics;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import model.Account;
import model.Order;
import model.dao.OrderDAO;
import model.helpers.Utils;

public class ComputeAnalytics {

	public static synchronized void computeOrders(HttpServletRequest req) {
		HttpSession s = req.getSession();
		Order order = (Order) s.getAttribute("order");
		order.computeAllCosts();
	}
	
	public static synchronized void addUserOrders(HttpServletRequest req) {
		HttpSession currentSession = req.getSession();
		
		Account ac = (Account) currentSession.getAttribute("AUTH");
		List<String> orders = (ArrayList<String>) currentSession.getAttribute("orders");
		String poDir = (String) req.getAttribute("poDir");

		if (orders != null) {
			OrderDAO orderDao = new OrderDAO(new File(poDir));
//			req.setAttribute("orderDao", new OrderDAO(new File(poDir)));
			req.setAttribute("totalAllOrders", orderDao.getPOs().length);

			if (ac != null && Utils.isValidUser(ac)) {
				currentSession.setAttribute("totalOrders", orderDao.getPOs(ac.getUsername()).length);

				for (File f: orderDao.getPOs(ac.getUsername())) {
					if (!orders.contains(f.getAbsolutePath())) {
						orders.add(f.getAbsolutePath());
					}
				}
			}
		}
	}
	
	public static synchronized void computerAvgStartCheckoutTime(HttpServletRequest req) {
		HttpSession currentSession = req.getSession();
		String url = req.getRequestURI();
		String path = url.replaceAll("/eFoods/", "");

		if (path.toLowerCase().contains("checkout") & req.getParameter("confirm") != null) {
			if (currentSession.getAttribute("checkoutTime") == null) {
				currentSession.setAttribute("checkoutTime", new Date());
			}

			Date startTime = (Date) currentSession.getAttribute("startTime");
			Date checkoutTime = (Date) currentSession.getAttribute("checkoutTime");

			long diff = checkoutTime.getTime() - startTime.getTime();
			long seconds = TimeUnit.MILLISECONDS.toSeconds(diff);
			long minutes = TimeUnit.MILLISECONDS.toMinutes(diff);
			long checkoutAvgTime = seconds;
			System.out.println("avg: "+ seconds);
//			if (seconds > 60) {
//				checkoutAvgTime = minutes;
//			}
			currentSession.setAttribute("checkoutAvgTime", checkoutAvgTime);
		}
	}
	
	public static synchronized void resetAttrsAfterCheckout(HttpServletRequest req) {
		HttpSession currentSession = req.getSession();
		currentSession.removeAttribute("checkoutTime");
	}

}
