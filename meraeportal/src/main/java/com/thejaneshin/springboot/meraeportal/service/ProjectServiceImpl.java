package com.thejaneshin.springboot.meraeportal.service;

import java.util.List;
import java.util.Optional;

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
	public List<Project> findAllUncompletedByUserId(int userId) {
		return projectRepository.findByUserIncomplete(userId);
	}

	@Override
	public List<Project> findAllCompletedByUserId(int userId) {
		return projectRepository.findByUserCompleted(userId);
	}

	@Override
	public Project findById(int theId) {
		Optional<Project> result = projectRepository.findById(theId);
		Project theProject = null;
		
		if (result.isPresent())
			theProject = result.get();
		else
			throw new RuntimeException("Did not find project id - " + theId);
		return theProject;
	}

	@Override
	public void save(Project theProject) {
		projectRepository.save(theProject);
	}

}
