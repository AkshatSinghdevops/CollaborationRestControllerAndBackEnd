package com.niit.dao;

import java.util.List;

import com.niit.model.User;

public interface UserDAO {
	
	

	public boolean update(User user);
	
	public User get(String id);
	
	public void setOnline(String userID);
	
	public void setOffLine(String userID);
	
	public boolean save(User user);
	
	public List<User> list();
	
	public User validate(String id,String password);
	
	public User getUser(String id);

	public List<User> list(String string);

}
