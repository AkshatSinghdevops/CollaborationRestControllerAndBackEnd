package com.niit.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.niit.dao.CommentDAO;
import com.niit.dao.ForumDAO;
import com.niit.model.Comment;
import com.niit.model.Forum;
import com.niit.model.User;

@RestController
public class CommentController {

	@Autowired
	ForumDAO forumDAO;
	/*@Autowired
	HttpSession session;*/
	
	@Autowired
	CommentDAO messageForumDAO; 
	
	/* @GetMapping
	 public @ResponseBody List<Comment> getCommentById()
	 {
		  List<Comment> = messageForumDAO.list();
	 }*/
	 
	
	 @RequestMapping(value="/addMessageforum",method=RequestMethod.POST)
		public ResponseEntity<Comment> createmessageforum(@RequestBody Comment messageforum,HttpSession session)
		{
			
	 String user=(String)session.getAttribute("loggedInUserID");
	  messageforum.setUser_id(user);
	  messageforum.setComment_date(new java.util.Date());
			if( messageforum.getId()==null)
			{
				
				 
				
				//messageforum.setForum_id();
				messageForumDAO.save(messageforum);
			}
			
			return new ResponseEntity<Comment>(messageforum,HttpStatus.OK);
		}
	 

	/* @RequestMapping(value="/list/message/forum",method=RequestMethod.GET)
		public ResponseEntity<java.util.List>  listmessageforum(Model model){
		   return new ResponseEntity(messageForumDAO.list(fid),HttpStatus.OK); 
		}
	 */
	 
	// @RequestMapping(value="lists",method=RequestMethod.GET)
	 //public @ResponseBody List<Comment> getallComment()
	 //{
		  //List<Comment> l = messageForumDAO.list(id);
	 //}
	 
	 
	
	 @RequestMapping(value="/list_message_forum/{id}",method=RequestMethod.GET)
		public ResponseEntity<java.util.List>  listmessageforum1(@PathVariable("id")String id ){
		   return new ResponseEntity(messageForumDAO.list(id),HttpStatus.OK); 
		}
	
	

	 @RequestMapping(value="/list_message_forum",method=RequestMethod.GET)
		public ResponseEntity<java.util.List>  listmessageforum(){
		   return new ResponseEntity(messageForumDAO.list(),HttpStatus.OK); 
		}
}

