package com.thejaneshin.springboot.meraeportal.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thejaneshin.springboot.meraeportal.dao.ProjectRepository;
import com.thejaneshin.springboot.meraeportal.entity.Project;

@Service
public class ProjectServiceImpl implements ProjectService {
	private ProjectRepository projectRepository;
	
	@Autowired
	public ProjectServiceImpl(ProjectRepository theProjectRepository) {
		projectRepository = theProjectRepository;
	}

	@Override
	public List<Project> findAllUnassigned() {
		return projectRepository.findByUserIsNullOrderByIdDesc();
	}

	@Override
	public List<Project> findAllByUserId(int userId) {
		return projectRepository.findByUserId(userId);
	}

	@Override
	public List<Project> findAllCompleted() {
		// TODO Auto-generated method stub
		return null;
	}

}
