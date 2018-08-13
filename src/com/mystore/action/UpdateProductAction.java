package com.mystore.action;

import java.io.File;
import java.nio.file.Files;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.mystore.dao.MyStoreDAO;
import com.mystore.dto.Product;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class UpdateProductAction extends ActionSupport implements ServletRequestAware, ModelDriven<Product> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger(UpdateProductAction.class);
	
	@Autowired
	MyStoreDAO myStoreDAO;
	private Product product = new Product();

	// Adding functionality for imageFileUpload
	private File productImage;
	private String productImageContentType;
	private String productImageFileName;

	private HttpServletRequest servletRequest;
	File fileToCreate=null;
	
	@Override
	public String execute() throws Exception {
		logger.info("Inside UpdateProductAction.execute()");
//		myStoreDao = new MyStoreDAOImpl();
		//Initializing SpringWebApp context and retrieving loginDAO bean
		WebApplicationContext context =	WebApplicationContextUtils.getRequiredWebApplicationContext(
	                                    ServletActionContext.getServletContext() );	
		myStoreDAO = (MyStoreDAO) context.getBean("myStoreDAO");
		
		

		logger.info("new product id-->"+product.getProduct_id());
		try {
		if(this.productImageFileName!=null) {
		String filePath = servletRequest.getSession().getServletContext().getRealPath("/resource/saved_image/");
		logger.info("Server path:" + filePath);
		 fileToCreate = new File(filePath, product.getProduct_id()+"_"+product.getProduct_name()+"_"+this.productImageFileName);
		if(Files.deleteIfExists(fileToCreate.toPath())){
			logger.info("File Deleted:" + fileToCreate.toPath());
		}
		FileUtils.copyFile(this.productImage, fileToCreate);
		
		}
		} catch(Exception e) {
		logger.error("Exception in update action",e);
		}
		
		
		
	logger.info("in action for updating -->"+product.getProduct_id()+","+ product.getProduct_name()+","+product.getRelated_products_name()+","+ product.getProduct_description()+","+ product.getProduct_id()+"_"+product.getProduct_name()+"_"+this.productImageFileName);

		
		
		
		product.setProduct_image(fileToCreate);
		product.setProduct_image_name(product.getProduct_id()+"_"+product.getProduct_name()+"_"+this.productImageFileName);
		
//		if(myStoreDAO.updateProduct(product.getProduct_id(), product.getProduct_name(), product.getProduct_description(),product.getRelated_products_name(),fileToCreate, product.getProduct_id()+"_"+product.getProduct_name()+"_"+this.productImageFileName)) {
	if(myStoreDAO.updateProduct(product)){
		logger.info("Product updated in backend... returning view");
		product.setMsg("Your record is updated");
			return "UPDATE_DATA";
		};
		return ERROR;
	}
	

	
	
	
	
	
	public File getProductImage() {
		return productImage;
	}


	public void setProductImage(File productImage) {
		this.productImage = productImage;

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