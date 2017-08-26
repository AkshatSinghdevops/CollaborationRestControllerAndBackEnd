package com.niit.dao;

import java.math.BigDecimal;
import java.util.List;

import com.niit.model.Comment;



public interface CommentDAO {
	public List<Comment> list();
	public List<Comment> list(String blogno);
	public boolean update(Comment comment) ;
	public Comment get(String userID, Integer commentID);
	public boolean delete(String userID, BigDecimal commentID);
	public boolean save(Comment comment);

}
