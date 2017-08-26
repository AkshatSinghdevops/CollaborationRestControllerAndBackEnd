package com.niit.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.niit.dao.JobDAO;
import com.niit.dao.UserDAO;
import com.niit.model.Job;
import com.niit.model.JobApplication;
import com.niit.model.User;

@RestController
public class MyJobController {
	
	@Autowired
	Job job;
	
	@Autowired
	JobDAO jobDAO;
	
	@Autowired
	User user;
	
	@Autowired
	JobApplication jobApplication;
	
	@Autowired
	UserDAO userDAO;
	
	@RequestMapping(value="/list",method=RequestMethod.GET)
	public @ResponseBody List<Job> getAllUsers()
	{
		List<Job> user = jobDAO.getAllOpenedJobs();
		return user;
	}
	
	@RequestMapping("jobs")
	public ResponseEntity<List<Job>> getAllJob() {

		List<Job> jobList = jobDAO.getAllOpenedJobs();
		return new ResponseEntity<List<Job>>(jobList, HttpStatus.OK);
	}

	/*@RequestMapping("job/apply")
	public ResponseEntity<JobApplication> applyJob(@RequestBody JobApplication jobApplication) {
		user = (User) session.getAttribute("user");
		System.out.println(user);
		
		jobApplication.setUserID(user.getId());
		jobApplication.setStatus('A');
		
		String j = job.getId();
		System.out.println(">>>>>>>>>>>"+j);
		
		jobApplication.setJobID(job.getId());
		Long d = System.currentTimeMillis();
		Date today = new Date(d);
		jobApplication.setDate_applied(today);
		System.out.println("abcd"+jobApplication.getId());
			if (jobApplicationDAO.save(jobApplication) == true) {
				jobApplication.setErrorCode("200");
				jobApplication.setErrorMessage("Job Applied");
			} else {
				jobApplication.setErrorCode("404");
				jobApplication.setErrorMessage("Job Applied Failed");
			}
			return new ResponseEntity<JobApplication>(jobApplication, HttpStatus.OK);
		
		

	}*/
	@RequestMapping("job/apply/{id}")
	public ResponseEntity<JobApplication> applyJob(@PathVariable("id") String id,HttpSession session) {
		user = (User) session.getAttribute("user");
		System.out.println(user);
		
		jobApplication.setUserID(user.getId());
		jobApplication.setStatus('A');
		
		//String j = job.getId();
		//System.out.println(">>>>>>>>>>>"+j);
		System.out.println(">>>>>>>>>>>"+id);
		
		jobApplication.setJobID(id);
		Long d = System.currentTimeMillis();
		Date today = new Date(d);
		jobApplication.setDate_applied(today);
		System.out.println("abcd"+jobApplication.getId());
			if (jobDAO.save(jobApplication) == true) {
				jobApplication.setErrorCode("200");
				jobApplication.setErrorMessage("Job Applied");
			} else {
				jobApplication.setErrorCode("404");
				jobApplication.setErrorMessage("Job Applied Failed");
			}
			return new ResponseEntity<JobApplication>(jobApplication, HttpStatus.OK);
		
		

	}
	@PostMapping("job/create")
	public ResponseEntity<Job> createJob(@RequestBody Job job) {
		if (jobDAO.getJobDetails(job.getId()) == null) {
			if (jobDAO.save(job) == true) {
				job.setErrorCode("200");
				job.setErrorMessage("Job created");
			} else {
				job.setErrorCode("404");
				job.setErrorMessage("Failed to create Job");
			}
			return new ResponseEntity<Job>(job, HttpStatus.OK);
		}
		job.setErrorCode("404");
		job.setErrorMessage("Job exist with this id " + job.getId());
		return new ResponseEntity<Job>(job, HttpStatus.OK);
	}
	@GetMapping("job/{id}")
	public ResponseEntity<Job> getJobById(@PathVariable("id") long id) {
		job = jobDAO.getJobDetails(id);

		if (job != null) {
			job.setErrorCode("200");
			job.setErrorMessage("Job found");

		} else {
			job = new Job();
			job.setErrorCode("404");
			job.setErrorMessage("Job not found with this id " + id);
		}
		return new ResponseEntity<Job>(job, HttpStatus.OK);
	}

	@RequestMapping("job/update")
	public ResponseEntity<Job> updateJob(@RequestBody Job job) {
		if (jobDAO.getJobById(job.getId()) != null) {
			if (jobDAO.save(job) == true) {
				job.setErrorCode("200");
				job.setErrorMessage("Job Updated");

			} else {
				job.setErrorCode("404");
				job.setErrorMessage("Job Updation Failed");
			}
			return new ResponseEntity<Job>(job, HttpStatus.OK);
		}
		job.setErrorCode("404");
		job.setErrorMessage("Job doesn't exist with this id " + job.getId());
		return new ResponseEntity<Job>(job, HttpStatus.OK);
	}
	@RequestMapping(value="/getJobdetails/{id}",method=RequestMethod.GET)
	public ResponseEntity<List<User>> getJobDetails(@PathVariable("id") String id){
		List<JobApplication> job=jobDAO.getJobApplication1(Long.parseLong(id.trim()));
		List<User> userlist=new ArrayList<User>();
        System.out.println(job);
		for(JobApplication j:job)
		{
		  String userid=j.getUserID();
		 User user= (User) userDAO.get(userid);
		 userlist.add(user); 
		 System.out.println(userlist);
		}
		
    	return new ResponseEntity<List<User>>(userlist,HttpStatus.OK);
	}
	@GetMapping("job-applied/{id}")
	public ResponseEntity<List<JobApplication>> getUserAppliedJob(@PathVariable("id") String id) {

		List<JobApplication> userAppliedJobs = jobDAO.getJobApplication1(Long.parseLong(id.trim()));
		return new ResponseEntity<List<JobApplication>>(userAppliedJobs, HttpStatus.OK);
	}
	
	@GetMapping("/listofapplieduser")
	public ResponseEntity<List<JobApplication>> getUserAppliedJob() {

		List<JobApplication> userAppliedJobs = jobDAO.listUserAppliedJob();
		return new ResponseEntity<List<JobApplication>>(userAppliedJobs, HttpStatus.OK);
	}
	
	
	
	/*@RequestMapping("applied-jobs")
	public ResponseEntity<List<JobApplication>> getAllAppledJob() {

		List<JobApplication> jobAppledList = jobApplicationDAO.list();
		return new ResponseEntity<List<JobApplication>>(jobAppledList, HttpStatus.OK);
	}

	@GetMapping("job-applied/{user_id}")
	public ResponseEntity<List<JobApplication>> getUserAppliedJob(@PathVariable("user_id") String user_id) {

		List<JobApplication> userAppliedJobs = jobApplicationDAO.getList(user_id);
		return new ResponseEntity<List<JobApplication>>(userAppliedJobs, HttpStatus.OK);
	}

	@PostMapping("job/create")
	public ResponseEntity<Job> createJob(@RequestBody Job job) {
		if (jobDAO.getJobById(job.getId()) == null) {
			if (jobDAO.save(job) == true) {
				job.setErrorCode("200");
				job.setErrorMessage("Job created");
			} else {
				job.setErrorCode("404");
				job.setErrorMessage("Failed to create Job");
			}
			return new ResponseEntity<Job>(job, HttpStatus.OK);
		}
		job.setErrorCode("404");
		job.setErrorMessage("Job exist with this id " + job.getId());
		return new ResponseEntity<Job>(job, HttpStatus.OK);
	}

	@GetMapping("job/{id}")
	public ResponseEntity<Job> getJobById(@PathVariable("id") String id) {
		job = jobDAO.getJobById(id);

		if (job != null) {
			job.setErrorCode("200");
			job.setErrorMessage("Job found");

		} else {
			job = new Job();
			job.setErrorCode("404");
			job.setErrorMessage("Job not found with this id " + id);
		}
		return new ResponseEntity<Job>(job, HttpStatus.OK);
	}

	@RequestMapping("job/update")
	public ResponseEntity<Job> updateJob(@RequestBody Job job) {
		if (jobDAO.getJobById(job.getId()) != null) {
			if (jobDAO.saveOrupdate(job) == true) {
				job.setErrorCode("200");
				job.setErrorMessage("Job Updated");

			} else {
				job.setErrorCode("404");
				job.setErrorMessage("Job Updation Failed");
			}
			return new ResponseEntity<Job>(job, HttpStatus.OK);
		}
		job.setErrorCode("404");
		job.setErrorMessage("Job doesn't exist with this id " + job.getId());
		return new ResponseEntity<Job>(job, HttpStatus.OK);
	}*/

}
