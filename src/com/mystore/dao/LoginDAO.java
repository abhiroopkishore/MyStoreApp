package com.mystore.dao;

import java.util.HashMap;

import javax.servlet.ServletContext;

public interface LoginDAO {

	public HashMap<String,String> retriveUserInfo(ServletContext ctx);
}
