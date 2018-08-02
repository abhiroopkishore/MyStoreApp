package com.mystore.action;

import java.io.File;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.mystore.dao.MyStoreDAO;




/**
 * Action class handles request to add products to list.
 */
public class AddProductAction extends ActionSupport implements ServletRequestAware {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(AddProductAction.class);
	
	@Autowired
	MyStoreDAO myStoreDAO;
	
	private String product_id, product_name, product_description,related_products_name;
	private String msg;

	// Adding functionality for imageFileUpload
	private File productImage;
	private String productImageContentType;
	private String productImageFileName;
	
	private HttpServletRequest servletRequest;
	
	@Override
	public String execute() throws Exception {
		logger.info("Inside AddProductAction.execute()");

		
		//Initializing SpringWebApp context and retrieving loginDAO bean
		WebApplicationContext context =	WebApplicationContextUtils.getRequiredWebApplicationContext(
	                                    ServletActionContext.getServletContext() );	
		myStoreDAO = (MyStoreDAO) context.getBean("myStoreDAO");
		
		

		try {

			setProduct_id(String.valueOf(myStoreDAO.generateProductId()));
			logger.info("Generated product ID: "+product_id);

			// Creating new filename and storing file in context path
			String filePath = servletRequest.getSession().getServletContext().getRealPath("/resource/saved_image/");
			logger.debug("Server path:" + filePath);
			String newFileName= product_id+"_"+product_name+"_"+this.productImageFileName;
			File fileToCreate = new File(filePath, newFileName);
			FileUtils.copyFile(this.productImage, fileToCreate);

			logger.debug("Details to be added in list -->"+product_id+","+ product_name+","+ product_description+","+related_products_name+","+ newFileName+".");

			// sending details to DAO service to add into list/DB
			if(myStoreDAO.addProduct(Integer.parseInt(product_id), product_name, product_description,related_products_name,fileToCreate, newFileName)) {
				logger.info("Product added in backend... returning view");
				msg="Your record is added";
				return "ADD_DATA";
			};


		}catch(Exception e) {
			logger.error("Exception occured in AddProductAction.execute() ", e);
			msg="Some error occured while adding your record in the backend";
			return ERROR;
		}
		return ERROR;

	}

	
	
	
	
	
// Getters/Setters for AddProduct form.	
	
	public String getProduct_id() {
		return product_id;
	}

	public void setProduct_id(String product_id) {
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

		
	public String getRelated_products_name() {
		return related_products_name;
	}

	public void setRelated_products_name(String related_products_name) {
		this.related_products_name = related_products_name;
	}








	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}








	public File getProductImage() {
		return productImage;
	}


	public void setProductImage(File productImage) {
		try{
		this.productImage = productImage;
		} catch(Exception e){
			this.productImage=null;
		}
	}


	public String getProductImageContentType() {
		return productImageContentType;
	}


	public void setProductImageContentType(String productImageContentType) {
		this.productImageContentType = productImageContentType;
	}


	public String getProductImageFileName() {
		return productImageFileName;
	}


	public void setProductImageFileName(String productImageFileName) {
		this.productImageFileName = productImageFileName;
	}

	
	
	@Override
	public void setServletRequest(HttpServletRequest servletRequest) {
		this.servletRequest = servletRequest;

	}
	
	
	
}
