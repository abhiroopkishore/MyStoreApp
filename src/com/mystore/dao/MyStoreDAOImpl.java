package com.mystore.dao;

import java.io.File;
import java.util.Collections;
import java.util.Comparator;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.mystore.dto.Product;
import com.mystore.dto.SyncProductList;


@Component("myStoreDAO")
public class MyStoreDAOImpl implements MyStoreDAO{

	private static final Logger logger = Logger.getLogger(MyStoreDAOImpl.class);
	private Product productBean =null;
	private SyncProductList splObj = new SyncProductList();
	
	
	public int generateProductId() {
		logger.info("Retreiving Product data from MyStoreDAO.generateProductId() ..");
		CopyOnWriteArrayList<Product> productBeanList = splObj.getProdBeanList();
		
		try{
			productBean = new Product();
		
			// custom sorting for Product list by ID and generating next product ID. Return 1 if list empty.
			if(!productBeanList.isEmpty()) {
				Product tempBean = Collections.max(productBeanList,new Comparator<Product>() {
																							@Override
																							public int compare(Product o1, Product o2) {
																							return Integer.compare(o1.getProduct_id(), o2.getProduct_id());
																							}
																						});
				return tempBean.getProduct_id()+1;
			}
			else {
				return 1;
			}
		} catch(Exception e){
			logger.error("Exception in MyStoreDAOImpl.generateProductId() ",e);
			return 0;
		}
	}
	
	
	
	
	// adding product to synchronized list
	@Override
	public boolean addProduct(int product_id, String product_name, String product_description, String related_products_name, File product_image, String product_image_name) {

		logger.info("Retreiving Product data from MyStoreDAO.addProduct() ..");
		logger.info("Adding --> "+product_id+","+product_name+","+product_description+","+product_image.getAbsoluteFile());
		CopyOnWriteArrayList<Product> productBeanList = splObj.getProdBeanList();
		
		try{

			productBean.setProduct_id(product_id);
			productBean.setProduct_name(product_name);
			productBean.setProduct_description(product_description);
			productBean.setProduct_image(product_image);
			productBean.setRelated_products_name(related_products_name);
			productBean.setProduct_image_name(product_image_name);
			productBeanList.add(productBean);
			
		} catch(Exception e){
			logger.error("Exception in MyStoreDAOImpl.addProduct() ",e);
			return false;
		}
		for(Product bean: productBeanList){
			logger.info("--> "+bean.getProduct_id() +" "+bean.getProduct_name()+" "+bean.getProduct_description()+","+bean.getProduct_image().getAbsoluteFile());
		}
		
		splObj.setProdBeanList(productBeanList);
		return true;
		
	}

	// Synchronized product list is ready and available. Will add any additional logic if needed.
	@Override
	public boolean fetchAllProductData() {
		logger.info("Retreiving Product data from MyStoreDAO.fetchAllProductData() ..");
		return true;
	}

	// remove product object from synchronized list
	@Override
	public boolean deleteProduct( int product_id) {
		logger.info("Deleting Product data from list in MyStoreDAO.deleteProduct() ..");
		logger.info("Deleting --> "+product_id);
		CopyOnWriteArrayList<Product> productBeanList = splObj.getProdBeanList();
		
		try{
			for(Product bean: productBeanList){
			logger.info("--> "+bean.getProduct_id() +" "+bean.getProduct_name()+" "+bean.getProduct_description());
			if(bean.getProduct_id()==product_id){
				productBeanList.remove(bean);
			}
		}
		}catch(Exception e){
			logger.error("Exception occured in MyStoreDAO.deleteProduct() ",e);
			return false;
		}
		splObj.setProdBeanList(productBeanList);
		return true;
	}

	
	// Update any product details into the list
	@Override
	public boolean updateProduct( int product_id, String product_name, String product_description,String related_products_name, File product_image, String product_image_name) {
		logger.info("Updating Product data in MyStoreDAO.updateProduct() ..");
		logger.debug("Updating --> "+product_id+","+product_name+","+product_description);
		CopyOnWriteArrayList<Product> productBeanList = splObj.getProdBeanList();
		Product tempBean = new Product();
		
		try{
			for(Product bean: productBeanList){
			logger.info("--> "+bean.getProduct_id() +" "+bean.getProduct_name()+" "+bean.getProduct_description());
			if(bean.getProduct_id()==product_id){
				tempBean.setProduct_id(product_id);
				tempBean.setProduct_name(product_name);
				tempBean.setProduct_description(product_description);
				tempBean.setRelated_products_name(related_products_name);
				
				if(product_image!=null) {
					logger.info("product_image has value--> "+product_image_name);
					tempBean.setProduct_image(product_image);
					tempBean.setProduct_image_name(product_image_name);
				} else {
					logger.info("product_image is null--> "+bean.getProduct_image_name());
					tempBean.setProduct_image(bean.getProduct_image());
					tempBean.setProduct_image_name(bean.getProduct_image_name());
				}
				
				productBeanList.remove(bean);
				productBeanList.add(tempBean);
			}
		}
		}catch(Exception e){
			logger.error("Exception occured in MyStoreDAO.updateProduct() ",e);
			return false;
		}
		
		splObj.setProdBeanList(productBeanList);
		return true;
	}

}
