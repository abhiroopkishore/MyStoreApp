package com.mystore.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts2.StrutsStatics;

import com.mystore.action.LoginAction;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;


/**
 * Login Interceptor checks with every request if user is logged in, before he's able to access other action class.
 */
public class LoginInterceptor extends AbstractInterceptor implements
StrutsStatics {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(LoginInterceptor.class);
	private static final String USER_HANDLE = "loggedInUser";

	public void init() {
		logger.info("Intializing LoginInterceptor...");
	}

	public void destroy() {
	}

	public String intercept(ActionInvocation invocation) throws Exception {

		final ActionContext context = invocation.getInvocationContext();
		HttpServletRequest request = (HttpServletRequest) context
				.get(HTTP_REQUEST);
		HttpSession session = request.getSession(true);

		// Is there a "user" object stored in the user's HttpSession?
		Object user = session.getAttribute(USER_HANDLE);
		if (user == null) {
			// The user has not logged in yet.
			logger.warn("USER IS NOT LOGGED IN!!");
			/* The user is attempting to log in. */
			if (invocation.getAction().getClass().equals(LoginAction.class))
			{
				return invocation.invoke();
			}
			return "login";
		} else {
			logger.info("USER -->"+user.toString());
			return invocation.invoke();
		}
	}

}
