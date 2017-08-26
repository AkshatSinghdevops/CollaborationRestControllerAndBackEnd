package com.niit.daoimpl;

import java.math.BigDecimal;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.niit.dao.FriendDAO;
import com.niit.model.Friend;




@Repository("friendDAO")
public class FriendDAOImpl implements FriendDAO {

	
	
	@Autowired
	private SessionFactory sessionFactory;


	//private static final Logger log = LoggerFactory.getLogger(UserDAOImpl.class);

	private Integer getMaxId() {

		String hql = "select max(id) from Friend";
		Query query = sessionFactory.openSession().createQuery(hql);
		Integer maxID;
		try {
			//log.debug("Enter into calculate MaxID");
			maxID = (Integer) query.uniqueResult();
			if (maxID == null) {
				maxID = 100;
			}
		} catch (NullPointerException e) {
			//log.debug("Enter In to MAXID Exception");
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 100;
		}

		return maxID;

	}

	public List<Friend> listMyFriend(String user_id) {
		String hql1 = "select friend_id  from Friend where user_id='" + user_id+ "' and status = 'Y' ";

		/* + " union  " + */

		String hql2 = "select user_id from Friend where friend_id='" + user_id+ "' and status = 'Y'";

		//log.debug("getMyFriends hql1 : " + hql1);
		//log.debug("getMyFriends hql2 : " + hql2);

		List<Friend> list1 = sessionFactory.openSession().createQuery(hql1).list();
		List<Friend> list2 = sessionFactory.openSession().createQuery(hql2).list();

		list1.addAll(list2);
//log.debug("array count"+list1.size());
		return list1;
	}

	public Friend get(String friend_id,String user_id) {
		String hql = "from Friend where user_id=" + "'" + user_id+ "' and friend_id= '" + friend_id + "' and status= 'R' ";

		//log.debug("hql: " + hql);
		Query query = sessionFactory.openSession().createQuery(hql);

		return (Friend) query.uniqueResult();
	}
	@Transactional
	public boolean save(Friend friend) {
		try {
			// log.debug("*********************Previous id " + getMaxId());
			friend.setId(getMaxId() + 1);
			//log.debug("***********************generated id:" + getMaxId());
			sessionFactory.getCurrentSession().save(friend);
			return true;
		} catch (Exception e) {
			//log.debug("Enter into SAVE EXCEPTION");
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	@Transactional
	public boolean update(Friend friend) {
		try {
			//log.debug("Starting of the method update");
			System.out.println("user ID : " + friend.getUser_id() + " Friend id :" + friend.getFriend_id());
			System.out.println("user ID : " + friend.getUser_id() + " Friend id :" + friend.getFriend_id());
			sessionFactory.getCurrentSession().update(friend);
			System.out.println("Successfully updated");
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Not able to update the status");
			return false;
		}
	}
	@Transactional
	public List<Friend> getNewFriendRequests(String user_id) {
		String hql = "select user_id from Friend where friend_id=" + "'" + user_id+ "' and status ='" + "R'";

		System.out.println(hql);
		 return  sessionFactory.openSession().createQuery(hql).list();
	}

	public boolean setOnline(String userid) {
		System.out.println("Starting of the metnod setOnline"+userid);
		
		//String hql = " UPDATE Friend	SET isOnline = 'Y' where friend_id='" + userid+ "'";
		
		try {
			String hql = " UPDATE Friend	SET is_online = 'Y' where friend_id= ?";
			
			
			
			
			System.out.println("hql: " + hql);
			Query query = sessionFactory.getCurrentSession().createQuery(hql);
			query.setString(0, userid);
			
			
			query.executeUpdate();
			System.out.println("Ending of the metnod setOnline"+query);
			return true;
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

	}
	@Transactional
	public boolean setOffLine(String userid) {
		try {
			System.out.println("Starting of the metnod setOffLine");
			String hql = " UPDATE Friend	SET is_online = 'N' where friend_id='" + userid+ "'";
			System.out.println("hql: " + hql);
			Query query = sessionFactory.openSession().createQuery(hql);
			query.executeUpdate();
			System.out.println("Ending of the metnod setOffLine");
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

	}
	@Transactional
	public List<Friend> getRequestsSendByMe(String userid) {
		String hql = "select friend_id from Friend where user_id=" + "'" + userid+ "' and status ='" + "R'";

		//log.debug(hql);
		return  sessionFactory.openSession().createQuery("From Friend").list();
	}


	@Transactional
	public List<Friend> getMyFriends(String user_id) {
		String hql1="select f from Friend f where f.user_id='"+user_id+"'and f.status='A'";
		String hql2="select f from Friend f where f.friend_id='"+user_id+"'and f.status='A'";
		Query query1=sessionFactory.openSession().createQuery(hql1);
		Query query2=sessionFactory.openSession().createQuery(hql2);
		List<Friend> list1=(List<Friend>)query1.list();
		List<Friend> list2=(List<Friend>)query2.list();
		list1.addAll(list2);
		return list1;
	}
	
	
}
