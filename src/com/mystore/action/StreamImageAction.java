package com.mystore.action;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.interceptor.ServletRequestAware;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.log4j.Logger;


/**
 * 
 */
public class StreamImageAction extends ActionSupport implements ServletRequestAware{

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(StreamImageAction.class);

	private HttpServletRequest servletRequest;
	byte[] imageInByte = null;
	String imageId;


	public String execute() {
		return SUCCESS;
	}

	public byte[] getCustomImageInBytes() {

		logger.info("imageId" + imageId);

		BufferedImage originalImage;
		try {
			originalImage = ImageIO.read(getImageFile(this.imageId));
			// convert BufferedImage to byte array
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(originalImage, "jpg", baos);
			baos.flush();
			imageInByte = baos.toByteArray();
			baos.close();
		} catch (IOException e) {
			logger.error("Exception occured while reading image stream.. ",e);
		}

		return imageInByte;
	}

	private File getImageFile(String imageId) {
		String filePath = servletRequest.getSession().getServletContext().getRealPath("/");
		File file = new File(filePath + "/resource/saved_image/", imageId);
		logger.debug("Image file: "+file.toString());
		return file;
	}

	public String getCustomContentType() {
		return "image/jpeg";
	}
//
//	public String getCustomContentDisposition() {
//		return "anyname.jpg";
//	}

	


	public String getImageId() {
		return imageId;
	}

	public void setImageId(String imageId) {
		this.imageId = imageId;
	}

//	public StreamImageAction() {
//		logger.info("StreamImageAction");
//	}
	
	
	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.servletRequest = request;

	}

}
