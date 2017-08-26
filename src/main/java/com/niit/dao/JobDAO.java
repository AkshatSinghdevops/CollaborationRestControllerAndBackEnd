package com.niit.dao;

import java.util.List;

import com.niit.model.Job;
import com.niit.model.JobApplication;

public interface JobDAO {



	public List<Job> getAllOpenedJobs();
    public Job getJobDetails(Long id);
    public boolean updateJob(Job job);
    public boolean updateJob(JobApplication jobApplication);
    public boolean save(JobApplication jobApplication);
    public boolean save(Job job);
    public List<JobApplication> getMyAppliedJobs(String userId);
    public JobApplication getJobApplication(String userID,String jobID);
    public JobApplication getJobApplication(Long jobID);
    public boolean delete(Job job);
    public List<JobApplication> getJobApplication1(Long jobID);

    public Job getJobById(long l);
	public List<JobApplication> listUserAppliedJob();

}

