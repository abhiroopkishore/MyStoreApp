package com.mystore.action;

import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.log4j.Logger;

import com.mystore.dto.Product;
import com.mystore.dto.SyncProductList;
import com.mystore.dto.User;
import com.opensymphony.xwork2.ActionSupport;

/**
 * Action class to fetch data for display in view
 */
public class FetchGridDataAction extends ActionSupport {

	private static final long serialVersionUID = -3929246494509709945L;
	private static final Logger logger = Logger.getLogger(FetchGridDataAction.class);


	private SyncProductList splObj = new SyncProductList();
	private User user =null;
	private CopyOnWriteArrayList<Product> productBeanList=null;
	




	@Override
	public String execute() throws Exception {
		logger.info("Inside FetchGridDataAction.execute()...");
		
		try {
			productBeanList = splObj.getProdBeanList();			
			for(Product bean: productBeanList) {
				logger.info("In action for adding -->"+bean.getProduct_id()+","+ bean.getProduct_name()+","+ bean.getProduct_description()+","+bean.getRelated_products_name()+","+bean.getProduct_image());
			}	
		} catch (Exception e) {
			logger.error("Exception occured in FetchGridDataAction.execute() ",e);
			return ERROR;
		}
		return "FETCH_DATA";
	}



	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}



	
	public CopyOnWriteArrayList<Product> getProductBeanList() {
		logger.debug("getProductBeanList() ID:--> "+productBeanList.get(0).getProduct_id());
		return productBeanList;
	}



	public void setProductBeanList(CopyOnWriteArrayList<Product> productBeanList) {
		this.productBeanList = productBeanList;
		logger.debug("setProductBeanList() ID:--> "+productBeanList.get(0).getProduct_id());
	}

	
	
	
	
	
  
	
}