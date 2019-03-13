package com.thejaneshin.springboot.meraeportal.service;

import java.util.List;

import com.thejaneshin.springboot.meraeportal.entity.Project;

public interface ProjectService {
	public List<Project> findAllUnassigned();
	
	public List<Project> findAllUncompletedByUserId(int userId);
	
	public List<Project> findAllCompletedByUserId(int userId);
	
	public Project findById(int theId);
	
	public void save(Project theProject);
	
	public void deleteById(int theId);
}
