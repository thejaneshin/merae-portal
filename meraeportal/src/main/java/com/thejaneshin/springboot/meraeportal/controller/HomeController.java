package com.thejaneshin.springboot.meraeportal.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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
	public String listProjects(Model theModel) {
		List<Project> unassignedProjects = projectService.findAllUnassigned();
		theModel.addAttribute("unassigned", unassignedProjects);
		
		User me = userService.getCurrentUser();
		theModel.addAttribute("me", me);
		
		List<Project> myProjects = projectService.findAllByUserId(me.getId());
		theModel.addAttribute("myProjects", myProjects);
		
		return "home";
	}
}
