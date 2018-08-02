package com.mystore.action;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.mystore.dao.MyStoreDAO;
import com.opensymphony.xwork2.ActionSupport;

/**
 *Delete Action class to remove records from product list
 */
public class DeleteProductAction extends ActionSupport {

	private static final long serialVersionUID = 8240266844869577238L;
	private static final Logger logger = Logger.getLogger(DeleteProductAction.class);
	
	@Autowired
	MyStoreDAO myStoreDAO;
	
	private int product_id;
	private String product_name, product_description;
	private String msg;

	

	@Override
	public String execute() throws Exception {
		logger.info("Inside DeleteProductAction.execute()");

		//Initializing SpringWebApp context and retrieving loginDAO bean
		WebApplicationContext context =	WebApplicationContextUtils.getRequiredWebApplicationContext(
	                                    ServletActionContext.getServletContext() );	
		myStoreDAO = (MyStoreDAO) context.getBean("myStoreDAO");
		
		
		if(myStoreDAO.deleteProduct(product_id)) {
			logger.info("Product deleted in backend... returning view");
			msg="Your record is deleted";
			return "DELETE_DATA";
		};
		return ERROR;
	}
	
	
	

	public int getProduct_id() {
		return product_id;
	}

	public void setProduct_id(int product_id) {
		this.product_id = product_id;
	}

	public String getProduct_name() {
		return product_name;
	}

	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}

	public String getProduct_description() {
		return product_description;
	}

	public void setProduct_description(String product_description) {
		this.product_description = product_description;
	}
	



	public String getMsg() {
		return msg;
	}



	public void setMsg(String msg) {
		this.msg = msg;
	}

}
