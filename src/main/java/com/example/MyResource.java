package com.example;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.apache.commons.io.IOUtils;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("uploadfile")
public class MyResource {

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt() {
        return "Got it!";
    }
    
    
    static private final String UPLOADED_FILE_PATH = "/home/android/tmp/";
    
    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces({MediaType.APPLICATION_JSON}) 
    public CommonResultEntity uploadFile2(
    		@FormDataParam("meid") String meid,
    		@FormDataParam("picname") String picname,
    		@FormDataParam("uploaddt") String uploaddt,
    		@FormDataParam("hisflag") String hisflag,
    		@FormDataParam("picsize") String picsize,
    		@FormDataParam("picchannel") String picchannel,
    		@FormDataParam("uploadfile") InputStream uploadedInputStream,
            @FormDataParam("uploadfile") FormDataContentDisposition fileDetail,
    		@FormDataParam("picOriginType") String picOriginType,
    		@FormDataParam("dbmLevel") String dbmLevel,
    		@FormDataParam("netType") String netType
    		)
    {
    	String uploadedFileLocation = UPLOADED_FILE_PATH + fileDetail.getFileName();
	
    	// save it
		if(writeToFile(uploadedInputStream, uploadedFileLocation))
    		return new CommonResultEntity(1, "ok");
    	else
    		return new CommonResultEntity(0, "error");

    }
    
    
 	private boolean writeToFile(InputStream uploadedInputStream,
 		String uploadedFileLocation) {

 		try {
 			FileOutputStream out = new FileOutputStream(new File(
 					uploadedFileLocation));
 			int read = 0;
 			byte[] bytes = new byte[1024];

 			out = new FileOutputStream(new File(uploadedFileLocation));
 			while ((read = uploadedInputStream.read(bytes)) != -1) {
 				out.write(bytes, 0, read);
 			}
 			out.flush();
 			out.close();
 			return true;
 		} catch (IOException e) {

 			e.printStackTrace();
 		}
 		return false;
 	}
}


class CommonResultEntity {

    private int errcode;
    private String errmsg;

    
    public CommonResultEntity(int code, String msg){
    	errcode = code;
    	errmsg = msg;
    }
    
    public int getErrcode() {
        return errcode;
    }

    public void setErrcode(int errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }
}
