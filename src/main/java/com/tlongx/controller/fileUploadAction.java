package com.tlongx.controller; 
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.HashMap; 
import java.util.Map; 
import javax.imageio.ImageIO;
import javax.servlet.ServletContext; 
import org.apache.commons.io.FileUtils; 
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.google.gson.Gson;     
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import com.tlongx.common.Const;
import com.tlongx.coomon.Result;
import com.tlongx.util.MUtil;
@Controller
@Scope("prototype")
@RequestMapping("/file")
public class fileUploadAction {
 
	private static final long serialVersionUID = 1L;
	/**
     * 文件上传
     * @param uploadFile
     * @param http://14.10.1.162:8080/aspire/file/fileUpload 
     **/
   	@SuppressWarnings("resource")
   	@RequestMapping(value="fileUpload")
   	public  String fileUpload(@RequestParam("uploadFile[]") File uploadFile[],@RequestParam("uploadFileContentType[]") String uploadFileContentType[],
   			@RequestParam("uploadFileFileName[]") String uploadFileFileName[]	){
       	 Map<String, Object> resultMap = new HashMap<String, Object>(); 
       	 Result result = new Result(); 
       
      	     StringBuffer imgPath = new StringBuffer("");
       	 if(uploadFile != null && uploadFile.length > 0)
            {   
       		WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();    
            ServletContext sc = webApplicationContext.getServletContext();  
                String path = sc.getRealPath("/upload");
                String datePath = (new StringBuilder("/")).append(MUtil.formDateFormat(new Date(), "yyyyMMdd")).toString();
                String relPath =  (new StringBuilder(String.valueOf(path))).append(datePath).toString();
                for(int i = 0; i < uploadFile.length; i++)
                {   
               	 String fileName = MUtil.uuid();  
               	 String subfix = uploadFileFileName[i].substring(uploadFileFileName[i].lastIndexOf("."));
               	 fileName = (new StringBuilder(String.valueOf(fileName))).append(subfix).toString();
                    imgPath.append((new StringBuilder("/upload")).append(datePath).append("/").append(fileName).append(",").toString());
                  
                    try
                    {  
                   	//new file
                        File file = new File(relPath, fileName);  
                        FileInputStream in = new FileInputStream(uploadFile[i]);
                        if(uploadFileContentType[i].equals("image/gif")||uploadFileContentType[i].equals("image/jpeg")||
                       		 uploadFileContentType[i].equals("image/jpg")||uploadFileContentType[i].equals("image/png")){
                       	 
   	                    // if file size more than 10 M 
   	                     if(in.available() > 1024*1024*10){
   	                    	 uploadFile[i].delete();
   	                    	 result.setStatus(Const.ErrorMessage.STATUS_VALID_FILE_IS_TOO_LONG_ALREADY.getCode());
   	                    	 result.setInfo(Const.ErrorMessage.STATUS_VALID_FILE_IS_TOO_LONG_ALREADY.getValue());
   	                     }
                        
   	                     //get file w & h
   	                     Image src = ImageIO.read(uploadFile[i]);
   	                     int w = src.getWidth(null);
   	                     int h = src.getHeight(null);
   	                     double scale;    
   	                     if(w > 0){    
   	                    	 scale=w/(double)w;    
   	                         h = (int) (h*scale);    
   	                     }else{    
   	                         if(h > 0){    
   	                        	 scale=h/(double)h;    
   	                             w = (int) (w*scale);    
   	                         }  
   	                     }
                         
   	                     //宽,高设定    
   	                     BufferedImage tag = null;  
   	                     if(subfix.equals("png")){  
   	                    	 tag = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);  
   	                     }else{  
   	                    	 tag = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);  
   	                     }  
   	                     Graphics2D graphics = tag.createGraphics();  
   		                     graphics.setBackground(new Color(255,255,255));  
   		                     graphics.setColor(new Color(255,255,255));  
   		                     graphics.fillRect(0, 0, w, h); 
   		                     graphics.drawImage(src, 0, 0, w, h, null); 
   		                     
   		                 FileUtils.copyFile(uploadFile[i], file);
   	                     //压缩之后临时存放位置    
   	                     FileOutputStream out = new FileOutputStream(file);    
   	                     JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);    
   	                     JPEGEncodeParam jep = JPEGCodec.getDefaultJPEGEncodeParam(tag);    
   	                     
   	                     //压缩质量     
   	                     jep.setQuality(1.0f, true);    
   	                     encoder.encode(tag, jep);    
   	                     out.close();    
   	                     }else{ 
   	                    	 if(in.available() > 1024*1024*100){
   	                        	 uploadFile[i].delete();
   	                        	 result.setStatus(Const.ErrorMessage.STATUS_VALID_FILE_IS_TOO_LONG_ALREADY.getCode());
   	   	                    	 result.setInfo(Const.ErrorMessage.STATUS_VALID_FILE_IS_TOO_LONG_ALREADY.getValue());
   	                         }else{
   		                    	 FileUtils.copyFile(uploadFile[i], file);  
   	                         }} 
                    }
                    catch(Exception e)
                    {
                    	 result.setStatus(Const.ErrorMessage.STATUS__FALIED.getCode());
	                    	 result.setInfo(Const.ErrorMessage.STATUS__FALIED.getValue());
               		 e.printStackTrace();
                    } 
                 
            } 
                imgPath = imgPath.delete(imgPath.length() - 1,imgPath.length()); 
                resultMap.put("imgPath", imgPath);}
                result.setData(resultMap);
               // sendReponeMessage(new Gson().toJson(result)); 
       	return null;
       }  
    
	
}
