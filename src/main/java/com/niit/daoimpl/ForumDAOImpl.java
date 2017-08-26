package com.niit.daoimpl;

import com.niit.dao.ForumDAO;

import java.math.BigDecimal;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.niit.dao.ForumDAO;
import com.niit.model.Forum;


@Repository("forumDAO")
public class ForumDAOImpl implements ForumDAO {
	
	
	@Autowired
	SessionFactory sessionFactory;
    
	
	@Transactional
	public List<Forum> list() {
		
		String hql="from Forum c";
		
		Query query=sessionFactory.openSession().createQuery(hql);
        
		return query.list();
	}
	@Transactional
	public Forum get(String userID, Integer forumID) {
		
String hql="select c from Forum c where c.user_id="+"'"+userID+"'and c.id='"+forumID+"'";
		
Query query=sessionFactory.openSession().createQuery(hql);
		
		return (Forum)query.list().get(0);
	
	}
	@Transactional
	public boolean update(Forum forum) {
		try{
			Session session=sessionFactory.getCurrentSession();
			session.update(forum);
			session.flush();
			return true;
		}catch(Exception e){
		e.printStackTrace();	
		return false;
		}
	}
	
	
	@Transactional	
	public boolean delete(String userID,BigDecimal chatforumID) {
		Forum chatForum=new Forum();
		chatForum.setId(userID);
		chatForum.setUser_id(userID);
		Session session=sessionFactory.openSession();
		session.delete(chatForum);
		session.flush();
		return true;
	}
	
	@Transactional
	public boolean save(Forum chatForum){
		try{
			String hql="select blog_comm_sqe.nextval from dual";
			Query query=sessionFactory.getCurrentSession().createSQLQuery(hql);
	BigDecimal		maxID=(BigDecimal) query.uniqueResult();
			chatForum.setId(maxID.toString());
			Session session=sessionFactory.getCurrentSession();
			session.save(chatForum);
			session.flush();
		    return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	public Forum getForumByID(int id) {
		Session session = sessionFactory.getCurrentSession();
		Forum forum = (Forum) session.get(Forum.class,id);
		return forum;
	}
	

}


