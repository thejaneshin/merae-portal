package com.thejaneshin.springboot.meraeportal.controller;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.thejaneshin.springboot.meraeportal.entity.Project;
import com.thejaneshin.springboot.meraeportal.entity.User;
import com.thejaneshin.springboot.meraeportal.service.ProjectService;
import com.thejaneshin.springboot.meraeportal.service.UserService;

@Controller
public class HomeController {
	private ProjectService projectService;
	private UserService userService;
	private List<String> statuses;
	
	public HomeController(ProjectService theProjectService, UserService theUserService) {
		projectService = theProjectService;
		userService = theUserService;
	}
	
	@PostConstruct
	protected void loadStatuses() {
		statuses = new LinkedList<>();
		statuses.add("N/A");
		statuses.add("First");
		statuses.add("Second");
		statuses.add("Third");
		statuses.add("Fourth");
		statuses.add("Fifth");
		statuses.add("Cancelled");
		statuses.add("Late");	
	}
	
	@GetMapping("/")
	public String listProjects(Model theModel) {
		User me = userService.getCurrentUser();
		theModel.addAttribute("me", me);
		
		List<Project> myIncompleteProjects = projectService.findAllUncompletedByUserId(me.getId());
		theModel.addAttribute("myIncompleteProjects", myIncompleteProjects);
		
		List<Project> myCompletedProjects = projectService.findAllCompletedByUserId(me.getId());
		theModel.addAttribute("myCompletedProjects", myCompletedProjects);
		
		return "home";
	}
	
	@GetMapping("/submit")
	public String submitProject(@RequestParam("projectId") int theId) {
		// Makes sure a user doesn't try to submit another person's project
		User me = userService.getCurrentUser();
		User projectUser = userService.findUserByProjectId(theId);
		
		// If no user is associated with the given project id
		if (projectUser == null)
			return "access-denied";
		else if (me.getId() == projectUser.getId()) {
			Project theProject = projectService.findById(theId);
			// If project is already completed
			if (theProject.getStatus().equals("Completed"))
				return "access-denied";
			theProject.setStatus("Completed");
			theProject.setSubmittedDate(LocalDate.now());
			projectService.save(theProject);
			return "redirect:/";
		}
		// If user is trying to submit someone else's project
		else
			return "access-denied";
	}
	
	@GetMapping("/updateStatus")
	public String updateStatus(@RequestParam("projectId") int theId, Model theModel) {
		// Makes sure a user doesn't try to update status of another person's project
		User me = userService.getCurrentUser();
		User projectUser = userService.findUserByProjectId(theId);
		
		// If no user is associated with the given project id
		if (projectUser == null)
			return "access-denied";
		else if (me.getId() == projectUser.getId()) {
			Project theProject = projectService.findById(theId);
			// If project is already completed
			if (theProject.getStatus().equals("Completed"))
				return "access-denied";
			theModel.addAttribute("project", theProject);
			theModel.addAttribute("statuses", statuses);
			return "update-status";
		}
		// If user is trying to update someone else's project
		else
			return "access-denied";
	}
	
	@PostMapping("/updateStatus")
	public String saveProject(@ModelAttribute("project") Project theProject, @RequestParam("projectId") int projectId) {
		Project p = projectService.findById(projectId);
		p.setStatus(theProject.getStatus());
		projectService.save(p);
		return "redirect:/";
	}
}
