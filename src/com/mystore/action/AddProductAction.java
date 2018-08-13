package com.mystore.action;

import java.io.File;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.mystore.dao.MyStoreDAO;
import com.mystore.dto.Product;




/**
 * Action class handles request to add products to list.
 */
public class AddProductAction extends ActionSupport implements ServletRequestAware, ModelDriven<Product> {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(AddProductAction.class);
	
	@Autowired
	MyStoreDAO myStoreDAO;
	
	private Product product = new Product();
	

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

			product.setProduct_id(myStoreDAO.generateProductId());
			logger.info("Generated product ID: "+product.getProduct_id());


			// Creating new filename and storing file in context path
			String filePath = servletRequest.getSession().getServletContext().getRealPath("/resource/saved_image/");
			logger.info("Server path:" + filePath);
			String newFileName= product.getProduct_id()+"_"+product.getProduct_name()+"_"+this.productImageFileName;
			File fileToCreate = new File(filePath, newFileName);
			FileUtils.copyFile(this.productImage, fileToCreate);
			logger.info("Details to be added in list -->"+product.getProduct_id()+","+ product.getProduct_name()+","+ product.getProduct_description()+","+product.getRelated_products_name()+","+ newFileName+".");

			product.setProduct_image(fileToCreate);
			product.setProduct_image_name(newFileName);
			
			// sending details to DAO service to add into list/DB
			if(myStoreDAO.addProduct(product)){
				logger.info("Product added in backend... returning view");
				product.setMsg("Your record is added");
				return "ADD_DATA";
			};


		}catch(Exception e) {
			logger.error("Exception occured in AddProductAction.execute() ", e);
			product.setMsg("Some error occured while adding your record in the backend");
			return ERROR;
		}
		return ERROR;

	}

	
	
	
	
	
// Getters/Setters for AddProduct form.	
	


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



	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}






	@Override
	public Product getModel() {
		return product;
	}





	
	
	
	
	
}