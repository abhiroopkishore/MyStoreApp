package com.mystore.dto;

import java.util.concurrent.CopyOnWriteArrayList;

public class SyncProductList {

	// To synchronize the product data throughout the application for all users.
	private static CopyOnWriteArrayList<Product> prodBeanList = new CopyOnWriteArrayList<Product> ();
	

	public CopyOnWriteArrayList<Product> getProdBeanList() {
		return prodBeanList;
	}

	public void setProdBeanList(CopyOnWriteArrayList<Product> prodBeanList) {
		SyncProductList.prodBeanList = prodBeanList;
	}

	public SyncProductList() {
		super();
	}

	
	
}