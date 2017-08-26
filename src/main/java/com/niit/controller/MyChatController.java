package com.niit.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.niit.dao.ChatDAO;
import com.niit.dao.UserDAO;
import com.niit.model.Chat;
import com.niit.model.User;


@RestController
public class MyChatController {
	
	@Autowired
	private User user;
	
	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private Chat chat;
	
	@Autowired
	private ChatDAO chatDAO;

	
	
	@GetMapping("chat/{friend_id}")
	public ResponseEntity<List<Chat>> getAllchat(@PathVariable("friend_id") String friend_id,HttpSession session) {
		user = (User) session.getAttribute("user");
		String userID = user.getId();
		List<Chat> chatList = chatDAO.getChatByFriend(userID,friend_id);

		return new ResponseEntity<List<Chat>>(chatList, HttpStatus.OK);
	}
	
	@RequestMapping(value = "chating/save", method = RequestMethod.POST)
	public ResponseEntity<Chat> createBlo(@RequestBody Chat chat,HttpSession session) {

		user = (User) session.getAttribute("user");
		chat.setUser_id(user.getId());
		chat.setCreate_date(new Date());
		
			if (chatDAO.save(chat) == true) {
				System.out.println("chat saved successs-----");
				chat.setErrorCode("200");
				chat.setErrorMessage("Chat Created");
			} else {
				System.out.println("chat saved failed-----");
				chat.setErrorCode("404");
				chat.setErrorMessage("Failed to create Chat");
			}
			return new ResponseEntity<Chat>(chat, HttpStatus.OK);
		
	}

}

