package com.niit.dao;

import java.util.List;

import com.niit.model.Friend;

public interface FriendDAO {
	public List<Friend> listMyFriend(String userid);

	public Friend get(String friendID,String userid);

	public boolean save(Friend friend);

	public boolean update(Friend friend);

	public List<Friend> getNewFriendRequests(String userid);

	public boolean setOnline(String userid);

	public boolean setOffLine(String userid);

	// select * from Friend where userID=? status = 'N'
	public List<Friend> getRequestsSendByMe(String userid);

	
	//public List<Friend> getMyFriends(String userID);
}
