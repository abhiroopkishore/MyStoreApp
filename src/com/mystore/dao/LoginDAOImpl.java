package com.mystore.dao;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;


@Component("loginDAO")
public class LoginDAOImpl implements LoginDAO{
	
	 private static final Logger logger = Logger.getLogger(LoginDAOImpl.class);
	
	/**
	 * In real world scenario, data would be retrieved from DB.
	 * Here, retrieving username/password from WebContent/resource/user.csv
	 * @return savedUserInfo
	 */
	 @Override
	public HashMap<String,String> retriveUserInfo(ServletContext ctx) {
		logger.info("Retrieving saved User Info... ");
		
		HashMap<String, String> savedUserInfo= new HashMap<String, String>();

		
		String csvFile = ctx.getRealPath("/resource/user.csv");
		
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";
        
        try {

            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {

                // use comma as separator
                String[] user = line.split(cvsSplitBy);
                logger.info("User [name= " + user[0] + " , password=" + user[1] + "]");
                savedUserInfo.put(user[0].replaceAll("^\"|\"$", ""), user[1].replaceAll("^\"|\"$", ""));
                
            }

        } catch (FileNotFoundException e) {
        	logger.error("Exception while reading User info from file! ",e);
        } catch (IOException e) {
        	logger.error("Exception while reading User info from file! ",e);
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                	logger.error("Exception while reading User info from file! ",e);
                }
            }
        }
		
		
		return savedUserInfo;
	}


}
