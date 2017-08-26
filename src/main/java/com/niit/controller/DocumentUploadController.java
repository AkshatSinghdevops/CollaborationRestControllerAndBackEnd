package com.niit.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.niit.model.User;

@Controller
@RequestMapping("Upload")
@MultipartConfig(fileSizeThreshold=1024*1024*10,    // 10 MB
maxFileSize=1024*1024*50,          // 50 MB
maxRequestSize=1024*1024*100)      // 100 MB
public class DocumentUploadController {
	
	

	@RequestMapping(value="/u",method=RequestMethod.GET)
	public @ResponseBody String getAll()
	{
		
		return "hiii";
	}
	

	@RequestMapping(value="/upload", method=RequestMethod.GET)
    public @ResponseBody String provideUploadInfo() {
        return "You can upload a file by posting to this same URL.";
    }

    @RequestMapping(value="/upload", method=RequestMethod.POST)
    public @ResponseBody void upload(@RequestParam("file") MultipartFile file,HttpSession session) throws ServletException,IOException {
		
		User loggedInUserID=(User)session.getAttribute("user");
		String pathname = "D:\\akshat_project\\RestController\\src\\main\\webapp\\static\\images\\"+loggedInUserID.getId()+".jpeg";
		
		String fileName="";
		try{
			 fileName = file.getOriginalFilename();
			byte[] bytes = file.getBytes();
			BufferedOutputStream bops = new BufferedOutputStream(new FileOutputStream(new File(pathname)));
			bops.write(bytes);
			bops.close();
			System.out.println("file name " + fileName);
			System.out.println("file name " + pathname);
		}
		catch(IOException e)
		{
			System.out.println(e);
		}
    }
    
   /* @RequestMapping(value="/upload", method=RequestMethod.POST)
    public @ResponseBody String handleFileUpload( 
            @RequestParam("file") MultipartFile file){
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                BufferedOutputStream stream = 
                        new BufferedOutputStream(new FileOutputStream(new File( "-uploaded")));
                stream.write(bytes);
                stream.close();
                return "You successfully uploaded " +  " into " +  "-uploaded !";
            } catch (Exception e) {
                return "You failed to upload " + " => " + e.getMessage();
            }
        } else {
            return "You failed to upload " +  " because the file was empty.";
        }
    }
    */
  
}
