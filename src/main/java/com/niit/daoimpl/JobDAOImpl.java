package com.niit.daoimpl;

import java.math.BigDecimal;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.niit.dao.JobDAO;
import com.niit.model.Job;
import com.niit.model.JobApplication;

@Repository("jobDAO")
public class JobDAOImpl implements JobDAO {

	@Autowired 
	SessionFactory sessionFactory;
	
	
	
	private Long getMaxId()
	{
		Long maxID=100L;
		String hql="select c_job_sequence.nextval from dual";
		Query query=sessionFactory.getCurrentSession().createQuery(hql);
		try{
			
			maxID=(Long) query.uniqueResult();
		}catch(Exception e){
			maxID=100L;
			e.printStackTrace();
		}return maxID+1;
	}

	@Transactional
	public List<Job> getAllOpenedJobs() {
		String hql="from Job ";  //where status='"+"V'"
		Query query=sessionFactory.getCurrentSession().createQuery(hql);
		List<Job> list=(List<Job>)query.list();
		return query.list();
	}
	
	@Transactional
	public List<JobApplication> listUserAppliedJob() {
		String hql ="from JobApplication";
		Query query=sessionFactory.getCurrentSession().createQuery(hql);
		List<Job> list=(List<Job>)query.list();
		return query.list();
	}

	@Transactional
	public Job getJobDetails(Long id) {
		return (Job) sessionFactory.getCurrentSession().get(Job.class,id);
	}

	@Transactional
	public boolean updateJob(Job job) {
		try{
			sessionFactory.getCurrentSession().update(job);
			return true;
		}catch(Exception e){
		e.printStackTrace();	
		return false;
		}
	}

	@Transactional
	public boolean updateJob(JobApplication jobApplication) {
		try{
			sessionFactory.getCurrentSession().update(jobApplication);
			return true;
		}catch(Exception e){
		e.printStackTrace();	
		return false;
		}
	}

	@Transactional
	public boolean save(JobApplication jobApplied) {
		try{
			String hql="select c_job_applied_sequence.nextval from dual";
			Query query=sessionFactory.getCurrentSession().createSQLQuery(hql);
			BigDecimal maxID=(BigDecimal) query.uniqueResult();
			jobApplied.setId(maxID.longValue());
			
			Session session=sessionFactory.getCurrentSession();
			session.save(jobApplied);
			session.flush();
		}catch(Exception e){
		e.printStackTrace();	
		return false;
		}return true;
	}

	@Transactional
	public boolean save(Job job) {
		try{
			String hql="select c_job_sequence.nextval from dual";
			Query query=sessionFactory.getCurrentSession().createSQLQuery(hql);
			BigDecimal maxID=(BigDecimal) query.uniqueResult();
			job.setId(maxID.longValue());
			
			Session session=sessionFactory.getCurrentSession();
			session.save(job);
			session.flush();
			
		}catch(Exception e){
		e.printStackTrace();	
		return false;
		}return true;
	}

	  
	@Transactional
	public List<JobApplication> getMyAppliedJobs(String userId) {
		String hql="from JobApplication j where j.userID='"+userId+"'";
		Query query=sessionFactory.getCurrentSession().createQuery(hql);
		List<JobApplication> list=query.list();
		return list;
	}

	@Transactional
	public JobApplication getJobApplication(String userID, String jobID) {
		String hql="from JobApplication where userID='"+userID+"' and jobID='"+jobID+"'";
		return (JobApplication) sessionFactory.getCurrentSession().createQuery(hql).uniqueResult();
	}

	@Transactional
	public JobApplication getJobApplication(Long jobID) {
		return (JobApplication) sessionFactory.getCurrentSession().get(JobApplication.class,jobID);
	}
	
	@Transactional
	public List<JobApplication> getJobApplication1(Long jobID) {
		String hql="select j from JobApplication j where j.jobID='"+jobID+"'";
		Query query=sessionFactory.getCurrentSession().createQuery(hql);
		List<JobApplication> list=query.list();
		return list;
	}
	
	@Transactional
	public boolean delete(Job job) {
		
		Session session=sessionFactory.openSession();
		session.delete(job);
		session.flush();
		return true;
	}
	
	public Job getJobById(String id) {
		 Job job = (Job) sessionFactory.getCurrentSession().get(Job.class, id);
				 
				 return job;
			}

	public Job getJobById(long l) {
		// TODO Auto-generated method stub
		return null;
	}

}




/*@Transactional
@Repository("jobDAO")
public class JobDAOimpl implements JobDAO {

	  //private static final Logger log = LoggerFactory.getLogger(JobDAO.class);
private  SessionFactory  sessionFactory;
	
	
	public JobDAOimpl(SessionFactory sessionFactory)
	{
		this.sessionFactory=sessionFactory;
	}
	
	
	

	public Job get(String id) {
		 Job job = (Job) sessionFactory.getCurrentSession().get(Job.class, id);
		 
		 return job;
	}

	public List<Job> list() {
		 return sessionFactory.getCurrentSession().createQuery("from Job").list();
		
	}
*//**\
 * If spring security not used
 *//*
	public boolean isValidCredentials(String id, String password) {
		Query query =  sessionFactory.getCurrentSession().createQuery("from Job where id =? and password = ? ");
		query.setString(0, id);
		query.setString(1, password);
		
		 if( query.uniqueResult() ==null)
		 {
			 return false;
		 }
		 else
		 {
			 return true;
		 }
		
	
	}
	@Transactional
	public boolean save(Job job) {
		try {
			sessionFactory.getCurrentSession().save(job);
			return true;
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	
	}
	@Transactional
	public boolean update(Job job) {
	try {
		sessionFactory.getCurrentSession().update(job);
		return true;
	} catch (HibernateException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return false;
	}
	
	@Transactional
	public boolean delete(String id)
	{
		//log.debug("Starting of the method delete");
		//log.debug("Trying to delte the record : " + id);
		try
		{
		
		sessionFactory.getCurrentSession().delete(get(id));
		//log.debug("successfully delted the record :" + id);
		}catch(Exception e)
		{
			//log.debug("record does not exist with the id " + id);
			return false;
			
		}
		//log.debug("Ending of the method delete");
		return true;
	}
	
	
	@Transactional
	public boolean saveOrupdate(Job job) {
		
		//log.debug("Starting of the Save Method");
		
			try {
				sessionFactory.getCurrentSession().saveOrUpdate(job);
			
				return true;
			} catch (HibernateException e) {
				e.printStackTrace();
				//log.debug("Ending of the Save Method");
				return false;
			}
		}




	public boolean updatejob(Job job) {
		try {
			sessionFactory.getCurrentSession().update(job);
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}




	public Job getJobById(String id) {
 Job job = (Job) sessionFactory.getCurrentSession().get(Job.class, id);
		 
		 return job;
	}
	
}
*/