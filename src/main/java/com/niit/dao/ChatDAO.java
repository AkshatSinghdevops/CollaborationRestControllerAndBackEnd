package com.niit.dao;

import java.util.List;

import com.niit.model.Chat;

public interface ChatDAO {

	
	public List<Chat>  list();
	
	public boolean save(Chat chat);
		
		public boolean update(Chat chat);
		
		public boolean delete(String id);

		public Chat get(String string);
		public List<Chat> getChatByFriend(String user_id,String friend_id);
}
