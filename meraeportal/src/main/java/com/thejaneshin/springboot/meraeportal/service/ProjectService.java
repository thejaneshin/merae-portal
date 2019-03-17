package com.thejaneshin.springboot.meraeportal.service;

import java.util.List;
import java.util.Map;

import com.thejaneshin.springboot.meraeportal.entity.Project;
import com.thejaneshin.springboot.meraeportal.entity.User;

public interface ProjectService {
	public List<Project> findAllIncompleteByUserId(int userId);
	
	public List<Project> findAllCompletedByUserId(int userId);
	
	public List<Project> findAllCancelledByUserId(int userId);
	
	public List<Project> findAllUnassigned();
	
	public Map<User, List<Project>> findAllIncompleteProjectsByEachDesigner();
	
	public List<Project> findAllCompleted();
	
	public List<Project> findAllCancelled();
	
	public Project findById(int theId);
	
	public void save(Project theProject);
	
	public void deleteById(int theId);
}
