package com.niit.dao;

import java.math.BigDecimal;
import java.util.List;

import com.niit.model.Blog;

public interface BlogDAO {

	
	
	public List<Blog> list();
	public Blog get(String user_id, Integer blog_id);
	public boolean update(Blog blog);
	public boolean delete(String user_id, BigDecimal blog_id);
	public boolean save(Blog blog);
	public List<Blog> userbloglist(String user_id);
}
