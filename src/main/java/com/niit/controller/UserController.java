package com.niit.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.support.ErrorMessage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.niit.dao.UserDAO;
import com.niit.model.User;

@RestController
public class UserController {
	
	
	
	
	
	@Autowired
	private UserDAO userDAO;
	
	
	@RequestMapping(value="/users",method=RequestMethod.GET)
	public @ResponseBody List<User> getAllUsers()
	{
		List<User> user = userDAO.list();
		return user;
	}
	
	@RequestMapping(value = "/user/validate/", method = RequestMethod.POST)
	public ResponseEntity<User> validateUser(@RequestBody User user,HttpSession session) {
		user = userDAO.validate(user.getId(), user.getPassword());
		if (user != null) {
			user.setErrorCode("200");
			user.setErrorMessage("You have successfully logged in.");
			session.setAttribute("user", user);
			session.setAttribute("loggedInUserID",user);
		} else {
			user = new User(); // Do wee need to create new user?
			user.setErrorCode("404");
			user.setErrorMessage("Invalid Credentials.  Please enter valid credentials");
		}
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	 @GetMapping("/user/logout")
	  public void logoutUser(HttpSession session)   {
		
		 session.invalidate();
	  
	 }
	
	@RequestMapping(value="/user/",method=RequestMethod.POST)
	public ResponseEntity<User> createUser(@RequestBody User user)
	{
		 if(userDAO.get(user.getId())==null)
		 {
			 if(userDAO.save(user) ==true)
			 {
				 user.setErrorCode("200");
				 user.setErrorMessage("Thank you for registration . you have successfully register as " + user.getRole());
				 
			 }
			 else
			 {
				user.setErrorCode("404");
				user.setErrorMessage("Could not complete the operatin please contact Admin");
			 }
			 return new ResponseEntity<User>(user, HttpStatus.OK);
		 }
		user.setErrorCode("404");
		user.setErrorMessage("User already exist with id : " + user.getId());
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}
		
		   
	
	 

}