package com.mystore.action;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

/**
 * Action class to display data after login
 */
public class DisplayProductAction extends ActionSupport implements ModelDriven<String>{


	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(DisplayProductAction.class);


	private String user_name =null;
	
	@Override
	public String execute() throws Exception {
		logger.info("Inside DisplayProductAction.execute()...");

		HttpServletRequest request = ServletActionContext.getRequest();
		setUser_name((String)request.getAttribute("user_name"));
		return "DISPLAY";
	}



	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}



	@Override
	public String getModel() {
		return user_name;
	}
	
	
}
