package com.niit.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.niit.dao.FriendDAO;
import com.niit.dao.UserDAO;
import com.niit.model.Friend;
import com.niit.model.User;

@RestController
public class FriendController {
	
	
	/*@Autowired
	HttpSession session;*/
	@Autowired
	UserDAO userDAO;
	@Autowired
	User user;

	//private static final Logger logger = LoggerFactory.getLogger(FriendController.class);
	@Autowired
	Friend friend;

	@Autowired
	FriendDAO friendDAO;

	// Need to change it as RequestMapping and ID should take from session
	@GetMapping("/friends/{id}")
	public ResponseEntity<List<Friend>> getMyFriend(@PathVariable("id") String id) {

		List<Friend> friend = friendDAO.listMyFriend(id);
		return new ResponseEntity<List<Friend>>(friend, HttpStatus.OK);

	}

	@RequestMapping(value = "/addFriend/{friendID}", method = RequestMethod.GET)
	public ResponseEntity<Friend> sendFriendRequest(@PathVariable("friendID") String friendID,HttpSession session) {
		//logger.debug("->->->->calling method sendFriendRequest");
		// String CurrentUser = (String)
		// httpSession.getAttribute("CurrentUser");
		user = (User) session.getAttribute("user");
		String logged_user_id = user.getId();
		friend.setUser_id(user.getId());
		friend.setFriend_id(friendID);
		friend.setStatus('R'); // N - New, R->Rejected, A->Accepted

		// Is the user already sent the request previous?

		// check whether the friend exist in user table or not
		if (isUserExist(friendID) == false) {
			friend.setErrorCode("404");
			friend.setErrorMessage("No user exist with the id :" + friendID);
		}

		else if (friendDAO.get(friendID, logged_user_id) != null) {
			System.out.println("Enter into Friend Request already send");
			friend.setErrorCode("404");
			friend.setErrorMessage("You already sent the friend request to " + friendID);

		} else {
			friendDAO.save(friend);

			friend.setErrorCode("200");
			friend.setErrorMessage("Friend request successfull.." + friendID);
		}

		return new ResponseEntity<Friend>(friend, HttpStatus.OK);

	}

	private boolean isUserExist(String id) {
		if (userDAO.get(id) == null)
			return false;
		else
			return true;
	}

	@RequestMapping(value = "/getMyFriendRequests", method = RequestMethod.GET)
	public ResponseEntity<List<Friend>> getMyFriendRequests(HttpSession session) {
		System.out.println("#######calling method getMyFriendRequests#######");
		System.out.println(session.getAttribute("loggedInUserID"));
		user = (User) session.getAttribute("user");
		String CurrentUser = user.getId();
		System.out.println(CurrentUser);
		List<Friend> myFriendRequests = friendDAO.getNewFriendRequests(CurrentUser);

		return new ResponseEntity<List<Friend>>(myFriendRequests, HttpStatus.OK);

	}

	@RequestMapping(value = "/myFriends", method = RequestMethod.GET)
	public ResponseEntity<List<Friend>> getMyFriends(HttpSession session) {
		System.out.println("************calling method getMyFriends**************");
		
		
		System.out.println(session.getAttribute("loggedInUserID"));
		
		
		
		User user = (User) session.getAttribute("loggedInUserID");
		String CurrentUser = user.getId();
		
		List<Friend> myFriends = new ArrayList<Friend>();
		if (CurrentUser == null) {
			friend.setErrorCode("404");
			friend.setErrorMessage("Please login to know your friends");
			myFriends.add(friend);
			return new ResponseEntity<List<Friend>>(myFriends, HttpStatus.OK);

			
		}

		System.out.println("getting friends of : " + CurrentUser);
		myFriends = friendDAO.listMyFriend(CurrentUser);
		System.out.println("getting friends size : " + myFriends.size());
		
		if (myFriends.isEmpty()) {
			System.out.println("Friends does not exsit for the user : " + CurrentUser);
			friend.setErrorCode("404");
			friend.setErrorMessage("You does not have any friends");
			myFriends.add(friend);
		}
		System.out.println("Send the friend list ");
		return new ResponseEntity<List<Friend>>(myFriends, HttpStatus.OK);
	}

	

	@RequestMapping(value = "/accepttFriend/{friendID}", method = RequestMethod.PUT)
	public ResponseEntity<Friend> acceptFriendFriendRequest(@PathVariable("friendID") String friendID,HttpSession session) {
		//logger.debug("->->->->calling method acceptFriendFriendRequest");
        
		friend = updateRequest(friendID, 'Y',session);
		return new ResponseEntity<Friend>(friend, HttpStatus.OK);

	}
	
	private Friend updateRequest(String friendID, char status,HttpSession session) {
		//logger.debug("Starting of the method updateRequest");
		user = (User) session.getAttribute("user");
		String CurrentUser = user.getId();
		//logger.debug("CurrentUser : " + CurrentUser);
		
		if(isFriendRequestAvailabe(friendID,session)==false)
		{
			friend.setErrorCode("404");
			friend.setErrorMessage("The request does not exist.  So you can not update to "+status);
		}
		
		
		else
			friend = friendDAO.get(CurrentUser, friendID);
		friend.setStatus(status); // N - New, R->Rejected, A->Accepted

		friendDAO.update(friend);

		friend.setErrorCode("200");
		friend.setErrorMessage(
				"Request from   " + friend.getUser_id() + " To " + friend.getFriend_id() + " has updated to :" + status);
		//logger.debug("Ending of the method updateRequest");
		return friend;

	}
	private boolean isFriendRequestAvailabe(String friendID,HttpSession session)
	{
		user = (User) session.getAttribute("user");
		String CurrentUser = user.getId();
		
		if(friendDAO.get(CurrentUser,friendID)==null)
			return false;
		else
			return true;
	}
	
	@RequestMapping(value = "/unFriend/{friendID}", method = RequestMethod.PUT)
	public ResponseEntity<Friend> unFriend(@PathVariable("friendID") String friendID,HttpSession session) {
		System.out.println("->->->->calling method unFriend");
		updateRequest(friendID, 'U',session);
		return new ResponseEntity<Friend>(friend, HttpStatus.OK);

	}
	
	@RequestMapping(value = "/rejectFriend/{friendID}", method = RequestMethod.PUT)
	public ResponseEntity<Friend> rejectFriendFriendRequest(@PathVariable("friendID") String friendID,HttpSession session) {
		System.out.println("->->->->calling method rejectFriendFriendRequest");

		updateRequest(friendID, 'C',session);
		return new ResponseEntity<Friend>(friend, HttpStatus.OK);

	}
}

