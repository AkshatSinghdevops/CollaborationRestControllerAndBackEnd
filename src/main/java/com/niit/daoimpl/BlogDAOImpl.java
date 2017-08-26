package com.niit.daoimpl;

import java.math.BigDecimal;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.niit.dao.BlogDAO;
import com.niit.model.Blog;




@Repository("blogDAO")
public class BlogDAOImpl implements BlogDAO {
	
	
	
	@Autowired
	SessionFactory sessionFactory;

	@Transactional
	private Integer getMaxId()
	{
		String hql="select c_blog_sequence.nextval from dual";
	Query query=sessionFactory.getCurrentSession().createSQLQuery(hql);
	Integer maxID;
     try{
			maxID=(Integer) query.uniqueResult();
		}catch(Exception e){
			e.printStackTrace();
			return 100;
		}return maxID+1;
	}
	
	@Transactional
	public List<Blog> list(){
		String hql="from Blog";
				Query query=sessionFactory.openSession().createQuery(hql);
		return query.list();
		
	}
	
	
	@Transactional
	public List<Blog> userbloglist(String userID){
		String hql="from Blog b where b.user_id="+"'"+userID+"'";
				Query query=sessionFactory.openSession().createQuery(hql);
		return query.list();
		
	}
	
	@Transactional
	public Blog get(String userID, Integer blogID) {
		String hql="select b from Blog b where b.user_id="+"'"+userID+"'and b.id='"+blogID+"'";
		Query query=sessionFactory.openSession().createQuery(hql);
		System.out.println("blogid="+blogID);
		System.out.println("userid="+userID);
		return (Blog)query.list().get(0);
	}

	@Transactional
	public boolean update(Blog blog) {
		try{
			Session session=sessionFactory.getCurrentSession();
			session.update(blog);
			session.flush();
			return true;
		}catch(Exception e){
		e.printStackTrace();	
		return false;
		}
	}
		
	@Transactional
	public boolean delete(String userID, BigDecimal blog_id) {
		Blog blog=new Blog();
		blog.setId(blog_id);
		blog.setUser_id(userID);
		Session session=sessionFactory.openSession();
		session.delete(blog);
		session.flush();
		return true;
	}
	
	@Transactional
	public boolean save(Blog blog){
		try{
			String hql="select c_blog_sequence.nextval from dual";
			Query query=sessionFactory.getCurrentSession().createSQLQuery(hql);
	        BigDecimal	maxID=(BigDecimal) query.uniqueResult();
			blog.setId(maxID);
			Session session=sessionFactory.getCurrentSession();
			session.save(blog);
			session.flush();
		    return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}

}