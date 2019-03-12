package com.thejaneshin.springboot.meraeportal.service;

import java.util.List;

import com.thejaneshin.springboot.meraeportal.entity.Project;

public interface ProjectService {
	public List<Project> findAllUnassigned();
	
	public List<Project> findAllByUserId(int userId);
	
	public List<Project> findAllCompleted();
}
