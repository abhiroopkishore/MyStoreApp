package com.mystore.action;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

/**
 * Action class to end session and logout.
 */
public class LogoutAction extends ActionSupport{

	private static final long serialVersionUID = 1L;


		public String execute() {

			ServletActionContext.getRequest().getSession().invalidate();
			addActionMessage("You are successfully logout!");
			return "logout";

		}
}
