package com.niit.daoimpl;
import com.niit.dao.CommentDAO;
import com.niit.model.Comment;

import java.math.BigDecimal;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;



@Repository("commentDAO")
public class CommentDAOImpl implements CommentDAO {

	@Autowired
	SessionFactory sessionFactory;
	
	
	
	@Transactional
	public List<Comment> list() {
		String hql = "from Comment";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		return query.list();
	}
	
	@Transactional
	private Integer getMaxId()
	{
		String hql="select blog_comm_sqe.nextval from dual";
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
	public List<Comment> list(String blogno){
		String hql="from Comment c where forum_id="+"'"+blogno+"'";
				Query query=sessionFactory.openSession().createQuery(hql);
		return query.list();
		
	}
	
	@Transactional
	public boolean update(Comment comment) {
		try{
			Session session=sessionFactory.getCurrentSession();
			session.update(comment);
			session.flush();
			return true;
		}catch(Exception e){
		e.printStackTrace();	
		return false;
		}
	}
	
	@Transactional
	public Comment get(String userID, Integer commentID) {
		String hql="select c from Comment c where c.user_id="+"'"+userID+"'and c.id='"+commentID+"'";
		Query query=sessionFactory.openSession().createQuery(hql);
		System.out.println("commentid="+commentID);
		System.out.println("userid="+userID);
		return (Comment)query.list().get(0);
	}
	
/*	@Transactional
	public Comment getCommentByForumId(){
		Session session = sessionFactory.getCurrentSession();
		Query query = session.get(Comment.class, )
	}*/

		
	@Transactional	
	public boolean delete(String userID, BigDecimal commentID) {
		Comment comment=new Comment();
		comment.setUser_id(userID);
		comment.setId(commentID);
		Session session=sessionFactory.openSession();
		session.delete(comment);
		session.flush();
		return true;
	}
	
	@Transactional
	public boolean save(Comment comment){
		try{
			String hql="select blog_comm_sqe.nextval from dual";
			Query query=sessionFactory.getCurrentSession().createSQLQuery(hql);
	BigDecimal		maxID=(BigDecimal) query.uniqueResult();
			comment.setId(maxID);
			Session session=sessionFactory.getCurrentSession();
			session.save(comment);
			session.flush();
		    return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
}
