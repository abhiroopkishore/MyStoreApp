package com.mystore.action;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.Result;


/**
 * Provides result for StreamImageAction to display image stream in JSP.
 */
public class CustomImageBytesResult implements Result {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(CustomImageBytesResult.class);

	public void execute(ActionInvocation invocation) throws Exception {
		try{
		logger.info("Inside CustomImageBytesResult.execute(). Preparing image stream response... ");
		StreamImageAction action = (StreamImageAction) invocation.getAction();
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType(action.getCustomContentType());
		response.getOutputStream().write(action.getCustomImageInBytes());
		response.getOutputStream().flush();
		} catch(Exception e){
			logger.error("Exceptoion in CustomImageBytesResult",e);
			throw e;
		}
	}

}
