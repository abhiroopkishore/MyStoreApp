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
import com.opensymphony.xwork2.ActionSupport;

public class UpdateProductAction extends ActionSupport implements ServletRequestAware  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger(UpdateProductAction.class);
	
	@Autowired
	MyStoreDAO myStoreDAO;
	
	private int product_id;
	private String product_name, product_description,related_products_name;
	private String msg;

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
		
		
//		setProduct_id(String.valueOf(myStoreDao.generateProductId()));
		logger.info("new product id-->"+product_id);
		try {
		if(this.productImageFileName!=null) {
		String filePath = servletRequest.getSession().getServletContext().getRealPath("/resource/saved_image/");
		logger.info("Server path:" + filePath);
		 fileToCreate = new File(filePath, product_id+"_"+product_name+"_"+this.productImageFileName);
		if(Files.deleteIfExists(fileToCreate.toPath())){
			logger.info("File Deleted:" + fileToCreate.toPath());
		}
		FileUtils.copyFile(this.productImage, fileToCreate);
		
		}
		} catch(Exception e) {
		logger.error("Exception in update action",e);
		}
		
		
		
	logger.info("in action for updating -->"+product_id+","+ product_name+","+related_products_name+","+ product_description+","+ product_id+"_"+product_name+"_"+this.productImageFileName);

		
		
		
		
		
		if(myStoreDAO.updateProduct(product_id, product_name, product_description,related_products_name,fileToCreate, product_id+"_"+product_name+"_"+this.productImageFileName)) {
			logger.info("Product updated in backend... returning view");
			msg="Your record is updated";
			return "UPDATE_DATA";
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
	
	
}
