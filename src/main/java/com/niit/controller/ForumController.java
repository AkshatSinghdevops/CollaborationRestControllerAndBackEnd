package com.niit.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.niit.dao.ForumDAO;
import com.niit.model.Forum;
import com.niit.model.User;

@RestController
public class ForumController {
	
	
	@Autowired
	ForumDAO forumDAO;
	
	/*@Autowired
	HttpSession session;*/
	
	   /* @RequestMapping(value="/forum",method=RequestMethod.GET)
		public ResponseEntity<List<Forum>>  getAllForum(){
		 
		 List<Forum> forumlist = forumDAO.list();
		   
		 return new ResponseEntity<List<Forum>>(forumlist,HttpStatus.OK) ;
		}*/
    
	    @RequestMapping(value="/forums",method=RequestMethod.GET)
		public @ResponseBody List<Forum> getAllForums()
		{
			List<Forum> u = forumDAO.list();
			return u;
		}
	    
	    @GetMapping("forum/{id}")
	    public ResponseEntity<Forum> getForumByID(@PathVariable("id") int id)
	    {
	    	Forum forum = forumDAO.getForumByID(id);
	    	if(forum==null)
	    	{
	    		forum.setErrorCode("404");
	    		forum.setErrorMessage("Sorry something is wrong");
	    	}
	    	else
	    	{
	    		forum.setErrorCode("200");
				forum.setErrorMessage("Success");
	    	}
	    	
	    	
	    	return new ResponseEntity<Forum> (forum,HttpStatus.OK);
	    }
	    @RequestMapping(value="/addforum",method=RequestMethod.POST)
		public ResponseEntity<Forum> createforum(@RequestBody Forum chatforum,HttpSession session)
		{
			
	     User id=(User)session.getAttribute("user");
	     
	     System.out.println("id is " + id.getId());
	     chatforum.setUser_id(id.getId());
	 
	  chatforum.setCreate_date(new java.util.Date());

			if( chatforum.getId()==null)
			{
				forumDAO.save(chatforum);
			}
			
			return new ResponseEntity<Forum>(chatforum,HttpStatus.OK);
		}
	 
	    
}
