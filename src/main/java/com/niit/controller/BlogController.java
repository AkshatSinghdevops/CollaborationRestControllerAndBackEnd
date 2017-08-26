package com.niit.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.niit.dao.BlogDAO;
import com.niit.model.Blog;
import com.niit.model.User;

@RestController
public class BlogController {
	
	
	@Autowired
	BlogDAO blogDAO; 
	
	/*@Autowired
	HttpSession session;
	*/
	@Autowired
	User user;
	
	
	@RequestMapping(value="list_blog",method=RequestMethod.GET)
	public ResponseEntity<java.util.List>  listBlog(Model model)
	{
	   
		return new ResponseEntity(blogDAO.list(),HttpStatus.OK); 
	
	}
	
	@RequestMapping("blogs")
	public ResponseEntity<List<Blog>> getAllBlog() {

		List<Blog> blogList = blogDAO.list();

		return new ResponseEntity<List<Blog>>(blogList, HttpStatus.OK);
	}
	
	@RequestMapping("userBlog")
	public ResponseEntity<List<Blog>> getuserBLOG(HttpSession session) {
        user = (User) session.getAttribute("user");
		List<Blog> blogList = blogDAO.userbloglist(user.getId());

		return new ResponseEntity<List<Blog>>(blogList, HttpStatus.OK);
	}
	
	@RequestMapping(value="/blog/create",method=RequestMethod.POST)
	public ResponseEntity<Blog> createBlog(@RequestBody Blog blog,HttpSession session)
	{
		 
		System.out.println("blog started");
		System.out.println("blog title:"+blog.getBlog_name());
		System.out.println("blog description:"+blog.getDescription());
		System.out.println("blog id:"+blog.getId());
		
        user = (User) session.getAttribute("loggedInUserID");
        blog.setUser_id(user.getId());
        blog.setCreate_date(new java.util.Date());
 
        
        System.out.println("blog id:"+blog.getUser_id());
        
		if( blog.getId()==null)
		{
			blogDAO.save(blog);
		}
		
		
		
		return new ResponseEntity<Blog>(blog,HttpStatus.OK);
	}
	
	@RequestMapping(value="/blog/manage",method=RequestMethod.POST)
    public ResponseEntity<Blog> editb(@RequestBody Blog blog)
    {
   	System.out.println("edit started");
    	
   	 if(blogDAO.update(blog)==true){
 			
		blog.setErrorCode("200");
		blog.setErrorMessage("updated successfully");
   	}
   	 else {
 			
	     blog.setErrorCode("404");
	     blog.setErrorMessage("cannot be updated");
 		} 
   	  return new ResponseEntity<Blog>(blog,HttpStatus.OK);
    }
}

