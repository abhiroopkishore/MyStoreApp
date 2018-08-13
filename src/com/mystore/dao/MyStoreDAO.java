package com.mystore.dao;

import java.io.File;

import com.mystore.dto.Product;

public interface MyStoreDAO {
	
	public boolean addProduct(Product product);
//	public boolean addProduct(int product_id, String product_name, String product_description,String related_products_name, File product_image, String product_image_name) ;
		
	public boolean fetchAllProductData();	
	
	public boolean deleteProduct( int product_id);
	
	public boolean updateProduct( Product product);
//	public boolean updateProduct( int product_id, String product_name, String product_description,String related_products_name, File product_image, String product_image_name);


	public int generateProductId();
	
}