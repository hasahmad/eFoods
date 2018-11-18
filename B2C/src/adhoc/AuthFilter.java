package adhoc;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Account;
import model.helpers.Utils;

/**
 * Servlet Filter implementation class AuthFilter
 */
@WebFilter("/AuthFilter")
public class AuthFilter implements Filter {

    /**
     * Default constructor. 
     */
    public AuthFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		HttpSession s = req.getSession();
		String url = req.getRequestURI();
		Account user = (Account) s.getAttribute("AUTH");

		String path = url.replaceAll("/eFoods/", "");

 		String userName = request.getParameter("name");
		String username = request.getParameter("user");

		if (userName != null && username != null) {
			user.setName(userName);
			user.setUsername(username);
		}

 		if (path.contains("Checkout") || path.contains("Login") || 
 				path.contains("PurchaseOrders") || 
 				req.getParameter("doLogin") != null) 
 		{
 			if (!Utils.isValidUser(user)) {
 				String redirectUrl = "https://www.eecs.yorku.ca/~roumani/servers/auth/oauth.cgi";
 				String params = "?back=" + req.getRequestURL();
 				resp.sendRedirect(redirectUrl + params);
 				return;
 			}
 		}

		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
