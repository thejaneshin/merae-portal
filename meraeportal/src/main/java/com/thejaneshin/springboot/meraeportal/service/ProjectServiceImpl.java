package com.thejaneshin.springboot.meraeportal.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thejaneshin.springboot.meraeportal.dao.ProjectRepository;
import com.thejaneshin.springboot.meraeportal.entity.Project;
import com.thejaneshin.springboot.meraeportal.entity.User;

@Service
public class ProjectServiceImpl implements ProjectService {
	private ProjectRepository projectRepository;
	private UserService userService;
	
	@Autowired
	public ProjectServiceImpl(ProjectRepository theProjectRepository, UserService theUserService) {
		projectRepository = theProjectRepository;
		userService = theUserService;
	}
	
	@Override
	public List<Project> findAllIncompleteByUserId(int userId) {
		return projectRepository.findByUserIncomplete(userId);
	}

	@Override
	public List<Project> findAllCompletedByUserId(int userId) {
		return projectRepository.findByUserCompleted(userId);
	}

	@Override
	public List<Project> findAllCancelledByUserId(int userId) {
		return projectRepository.findByUserCancelled(userId);
	}
	@Override
	public List<Project> findAllUnassigned() {
		return projectRepository.findByUserIsNullOrderByIdDesc();
	}
	
	@Override
	public Map<User, List<Project>> findAllIncompleteProjectsByEachDesigner() {
		// Use LinkedHashMap to keep the order
		Map<User, List<Project>> res = new LinkedHashMap<>();
		
		List<User> designers = userService.findAllDesigners();
		
		for (User d : designers) {
			List<Project> eachIncomplete = projectRepository.findByUserIncomplete(d.getId());
			res.put(d, eachIncomplete);
		}
		
		return res;
	}
	
	@Override
	public List<Project> findAllCompleted() {
		return projectRepository.findAllCompleted();
	}
	
	@Override
	public List<Project> findAllCancelled() {
		return projectRepository.findAllCancelled();
	}
	
	@Override
	public Project findById(int theId) {
		Optional<Project> result = projectRepository.findById(theId);
		Project theProject = null;
		
		if (result.isPresent())
			theProject = result.get();
		else
			theProject = null;
		return theProject;
	}

	@Override
	public void save(Project theProject) {
		projectRepository.save(theProject);
	}

	@Override
	public void deleteById(int theId) {
		projectRepository.deleteById(theId);
	}

}
