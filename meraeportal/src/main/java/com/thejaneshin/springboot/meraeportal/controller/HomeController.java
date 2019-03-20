package com.thejaneshin.springboot.meraeportal.controller;

import java.time.LocalDate;
import java.util.List;

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
	
	public HomeController(ProjectService theProjectService, UserService theUserService) {
		projectService = theProjectService;
		userService = theUserService;
	}
	
	@GetMapping("/")
	public String listMyProjects(Model theModel) {
		User me = userService.getCurrentUser();
		theModel.addAttribute("me", me);
		
		List<Project> myIncompleteProjects = projectService.findAllIncompleteByUserId(me.getId());
		theModel.addAttribute("myIncompleteProjects", myIncompleteProjects);
		
		List<Project> myCompletedProjects = projectService.findAllCompletedByUserId(me.getId());
		theModel.addAttribute("myCompletedProjects", myCompletedProjects);
		
		List<Project> myCancelledProjects = projectService.findAllCancelledByUserId(me.getId());
		theModel.addAttribute("myCancelledProjects", myCancelledProjects);
		
		return "home/index";
	}
	
	@GetMapping("/updateStatus")
	public String updateStatus(@RequestParam("projectId") int projectId, Model theModel) {
		// Makes sure a user doesn't try to update status of another person's project
		User me = userService.getCurrentUser();
		User projectUser = userService.findUserByProjectId(projectId);
		
		// If no user associated or different user is trying to access
		if (projectUser == null || me.getId() != projectUser.getId())
			return "access-denied";
		else {
			Project theProject = projectService.findById(projectId);
			// If project is already completed or cancelled
			if (theProject.getStatus().equals("Completed") || theProject.getSubmittedDate() != null ||
					theProject.getStatus().equals("Cancelled") || theProject.getCancelledDate() != null)
				return "access-denied";
			
			theModel.addAttribute("project", theProject);
			return "home/update-status";
		}
	}
	
	@PostMapping("/updateStatus")
	public String updateStatus(@ModelAttribute("project") Project theProject, @RequestParam("projectId") int projectId) {
		Project p = projectService.findById(projectId);
		p.setStatus(theProject.getStatus());
		projectService.save(p);
		return "redirect:/";
	}
	
	@GetMapping("/submit")
	public String submitMyProject(@RequestParam("projectId") int projectId) {
		User me = userService.getCurrentUser();
		User projectUser = userService.findUserByProjectId(projectId);
		
		if (projectUser == null || me.getId() != projectUser.getId())
			return "access-denied";
		else {
			Project theProject = projectService.findById(projectId);
			if (theProject.getStatus().equals("Completed") || theProject.getSubmittedDate() != null ||
					theProject.getStatus().equals("Cancelled") || theProject.getCancelledDate() != null)
				return "access-denied";
			
			theProject.setStatus("Completed");
			theProject.setSubmittedDate(LocalDate.now());
			projectService.save(theProject);
			return "redirect:/";
		}
	}
	
	@GetMapping("/undoComplete")
	public String undoCompleteMyProject(@RequestParam("projectId") int projectId) {
		User me = userService.getCurrentUser();
		User projectUser = userService.findUserByProjectId(projectId);

		if (projectUser == null || me.getId() != projectUser.getId())
			return "access-denied";
		else {
			Project theProject = projectService.findById(projectId);
			if (!theProject.getStatus().equals("Completed") || theProject.getSubmittedDate() == null)
				return "access-denied";
			
			theProject.setStatus("N/A");
			theProject.setSubmittedDate(null);
			projectService.save(theProject);
			return "redirect:/";
		}
	}
}
