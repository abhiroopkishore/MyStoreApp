package com.mystore.dto;

import java.io.File;

public class Product {

		private int product_id;
		private String product_name;
		private String product_description;
		private String related_products_name;  // considering this to be a String from a single textbox like other fields
		private File product_image ;
		private String product_image_name;
		
		
		
		
		public String getProduct_image_name() {
			return product_image_name;
		}
		public void setProduct_image_name(String product_image_name) {
			this.product_image_name = product_image_name;
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
		public File getProduct_image() {
			return product_image;
		}
		public void setProduct_image(File product_image) {
			this.product_image = product_image;
		}
		
		public Product(int product_id, String product_name, String product_description, String related_products_name,
				File product_image) {
			super();
			this.product_id = product_id;
			this.product_name = product_name;
			this.product_description = product_description;
			this.related_products_name = related_products_name;
			this.product_image = product_image;
		}
		
		public Product() {
			super();
		}

	
		
		
}
