package com.niit.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

@Entity
@Table(name="C_FRIEND")
@Component
public class Friend extends BaseDomain{
	
	
	@Id
	private int id;
	
	private String user_id;
	
	private String friend_id;
	
	private char status;
	
	private char is_online;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getFriend_id() {
		return friend_id;
	}

	public void setFriend_id(String friend_id) {
		this.friend_id = friend_id;
	}

	public char getStatus() {
		return status;
	}

	public void setStatus(char status) {
		this.status = status;
	}

	public char getIs_online() {
		return is_online;
	}

	public void setIs_online(char is_online) {
		this.is_online = is_online;
	}
	
	
	

}


