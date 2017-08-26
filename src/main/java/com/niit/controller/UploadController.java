package com.niit.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.niit.model.User;

@RestController
@RequestMapping("api")
public class UploadController {

  
    
 
   
	@RequestMapping(value = "/upload",method=RequestMethod.POST)
	public @ResponseBody void upload(@RequestParam("file") MultipartFile file,HttpSession session) throws ServletException,IOException {
		
		User loggedInUserID=(User)session.getAttribute("user");
		String pathname = "D:\\akshat_project\\BackEndDemo\\src\\main\\webapp\\static\\images\\"+loggedInUserID.getId()+".jpeg";
		
		String fileName="";
		try{
			 fileName = file.getOriginalFilename();
			byte[] bytes = file.getBytes();
			BufferedOutputStream bops = new BufferedOutputStream(new FileOutputStream(new File(pathname+fileName)));
			bops.write(bytes);
			bops.close();
		}
		catch(IOException e)
		{
			System.out.println(e);
		}
		
		
		/*User loggedInUserID=(User)session.getAttribute("user");
		
		FileOutputStream fout=new FileOutputStream("D:\\akshat_project\\BackEndDemo\\src\\main\\webapp\\static\\images\\"+loggedInUserID.getId()+".jpeg");
		
		for (CommonsMultipartFile aFile : fileUpload){
              
	            System.out.println("Saving file: " + aFile.getOriginalFilename());
	             
	         byte[] bt=aFile.getBytes();
	         fout.write(bt);
	         fout.flush();
	        }*/
} 
}
	