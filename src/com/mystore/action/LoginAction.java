package com.mystore.action;




import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.mystore.dao.LoginDAO;
import com.mystore.dto.User;
import com.opensymphony.xwork2.ActionSupport;

/**
 * Action class for User login. User data saved in user.CSV file inside "/resource".
 */
public class LoginAction extends ActionSupport implements ServletRequestAware{

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(LoginAction.class);
	public HttpServletRequest request;	
		
	private User user;
	private HashMap<String, String> savedUserInfo;
	
	// Using Spring DI
	@Autowired
	LoginDAO loginDAO;
	

	public String execute() {
		logger.info("in Action");
		
		//Initializing SpringWebApp context and retrieving loginDAO bean
		WebApplicationContext context =	WebApplicationContextUtils.getRequiredWebApplicationContext(
	                                    ServletActionContext.getServletContext() );	
		loginDAO = (LoginDAO) context.getBean("loginDAO");
		
		
		try {
		//Retrieving saved user data from file
			savedUserInfo = loginDAO.retriveUserInfo(request.getServletContext());
		} catch(Exception e) {
			logger.error("Exception occured in  LoginAction!", e);
		}
		
		for(String key: savedUserInfo.keySet() ) {
			logger.info("SavedValue:- "+key+" : "+savedUserInfo.get(key));
			logger.info("EnteredCred:- "+user.getUname()+":"+user.getPassword());
			
			if(user.getUname().equals(key) && user.getPassword().equals(savedUserInfo.get(key)))
			{
				logger.info("Success in Action");

				
				ServletActionContext.getRequest().getSession().setAttribute("loggedInUser", user.getUname());
				return SUCCESS;

			}
		}
				
		// If for loop is over and no match found, then return error page.
			logger.error("Error in Action");
			return ERROR;
	}
	
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request=request;
		
	}

	
}
