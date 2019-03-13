package com.thejaneshin.springboot.meraeportal.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.thejaneshin.springboot.meraeportal.entity.Project;
import com.thejaneshin.springboot.meraeportal.service.ProjectService;

@Controller
@RequestMapping("/employees/projects")
public class EmployeeProjectController {
	private ProjectService projectService;
	
	public EmployeeProjectController(ProjectService theProjectService) {
		projectService = theProjectService;
	}
	
	@GetMapping("")
	public String listProjects(Model theModel) {
		List<Project> unassignedProjects = projectService.findAllUnassigned();
		theModel.addAttribute("unassigned", unassignedProjects);
		
		return "employees/projects/index";
	}
	
	@GetMapping("/add")
	public String addProject() {
		return "employees/projects/add";
	}
	
	@GetMapping("/delete")
	public String deleteProject(@RequestParam("projectId") int theId) {
		if (projectService.findById(theId) == null)
			return "access-denied";
		
		projectService.deleteById(theId);
		return "redirect:/employees/projects";
	}
	
}
