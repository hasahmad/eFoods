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
	
	public static synchronized void computeAvgStartCheckoutTime(HttpServletRequest req) {
		HttpSession currentSession = req.getSession();
		String url = req.getRequestURI();
		String path = url.replaceAll("/eFoods/", "");
		
		long checkoutAvgTime = 0;
		int checkoutAvgCount = 0;
		Order order = (Order) currentSession.getAttribute("order");
		
		if (path.toLowerCase().contains("checkout") && req.getParameter("confirm") != null && !order.getItems().isEmpty()) {
			currentSession.setAttribute("checkoutTime", new Date());

			if (currentSession.getAttribute("checkoutAvgTime") != null) {
				checkoutAvgTime = (long) currentSession.getAttribute("checkoutAvgTime");
			}
			
			if (currentSession.getAttribute("checkoutAvgCount") != null) {
				checkoutAvgCount = (int) currentSession.getAttribute("checkoutAvgCount");
			}

			Date startTime = (Date) currentSession.getAttribute("startTime");
			Date checkoutTime = (Date) currentSession.getAttribute("checkoutTime");

			long diff = checkoutTime.getTime() - startTime.getTime();
			long seconds = TimeUnit.MILLISECONDS.toSeconds(diff);

			checkoutAvgCount++;
			checkoutAvgTime = (checkoutAvgTime + seconds) / checkoutAvgCount;

			currentSession.setAttribute("checkoutAvgTime", checkoutAvgTime);
			currentSession.setAttribute("checkoutAvgCount", checkoutAvgCount);
		}
	}
	
	public static synchronized void computeAvgAddToCartTime(HttpServletRequest req) {
		HttpSession currentSession = req.getSession();
		String url = req.getRequestURI();
		String path = url.replaceAll("/eFoods/", "");

		long addToCartAvgTime = 0;
		int addToCartAvgCount = 0;
		Order order = (Order) currentSession.getAttribute("order");
		
		if (path.toLowerCase().contains("addtocart") && order.getItems().isEmpty()) {
			currentSession.setAttribute("addToCartTime", new Date());
			
			if (currentSession.getAttribute("addToCartAvgTime") != null) {
				addToCartAvgTime = (long) currentSession.getAttribute("addToCartAvgTime");
			}
			
			if (currentSession.getAttribute("addToCartAvgCount") != null) {
				addToCartAvgCount = (int) currentSession.getAttribute("addToCartAvgCount");
			}
			
			Date startTime = (Date) currentSession.getAttribute("startTime");
			Date addToCartTime = (Date) currentSession.getAttribute("addToCartTime");
			
			long diff = addToCartTime.getTime() - startTime.getTime();
			long seconds = TimeUnit.MILLISECONDS.toSeconds(diff);
			
			addToCartAvgCount++;
			addToCartAvgTime = (addToCartAvgTime + seconds) / addToCartAvgCount;

			currentSession.setAttribute("addToCartAvgTime", addToCartAvgTime);
			currentSession.setAttribute("addToCartAvgCount", addToCartAvgCount);
		}
	}
	
	public static synchronized void resetAttrsAfterCheckout(HttpServletRequest req) {
		HttpSession currentSession = req.getSession();
		currentSession.removeAttribute("checkoutTime");
	}

}
