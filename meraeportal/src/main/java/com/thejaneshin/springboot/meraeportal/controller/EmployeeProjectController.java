package com.thejaneshin.springboot.meraeportal.controller;


import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.thejaneshin.springboot.meraeportal.entity.Project;
import com.thejaneshin.springboot.meraeportal.entity.User;
import com.thejaneshin.springboot.meraeportal.service.ProjectService;
import com.thejaneshin.springboot.meraeportal.service.UserService;

@Controller
@RequestMapping("/employees/projects")
public class EmployeeProjectController {
	private ProjectService projectService;
	private UserService userService;
	
	public EmployeeProjectController(ProjectService theProjectService, UserService theUserService) {
		projectService = theProjectService;
		userService = theUserService;
	}
	
	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
		dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
	}
	
	@GetMapping("")
	public String listProjects(Model theModel) {
		List<Project> unassignedProjects = projectService.findAllUnassigned();
		theModel.addAttribute("unassigned", unassignedProjects);
		
		Map<User, List<Project>> incompleteEach = projectService.findAllIncompleteProjectsByEachDesigner();
		theModel.addAttribute("incompleteEach", incompleteEach);
		
		List<Project> completedProjects = projectService.findAllCompleted();
		theModel.addAttribute("completed", completedProjects);
		
		List<Project> cancelledProjects = projectService.findAllCancelled();
		theModel.addAttribute("cancelled", cancelledProjects);
		
		return "employees/projects/index";
	}
	
	@GetMapping("/add")
	public String addProject(Model theModel) {
		Project theProject = new Project();
		theModel.addAttribute("project", theProject);
		return "employees/projects/add";
	}

	@GetMapping("/update")
	public String updateProject(@RequestParam("projectId") int projectId, Model theModel) {
		Project theProject = projectService.findById(projectId);
		if (theProject == null || theProject.getStatus().equals("Completed") || theProject.getSubmittedDate() != null ||
				theProject.getStatus().equals("Cancelled") || theProject.getCancelledDate() != null)
			return "access-denied";
		
		theModel.addAttribute("project", theProject);
		return "employees/projects/add";
	}
	
	@PostMapping("/save")
	public String saveProject(@Valid @ModelAttribute("project") Project theProject, BindingResult theBindingResult) {
		if (theBindingResult.hasErrors())
			return "employees/projects/add";
		
		if (theProject.getStatus().equals("Cancelled"))
			theProject.setCancelledDate(LocalDate.now());
		
		projectService.save(theProject);
		return "redirect:/employees/projects";
	}
	
	@GetMapping("/delete")
	public String deleteProject(@RequestParam("projectId") int projectId) {
		Project theProject = projectService.findById(projectId);
		if (theProject == null || theProject.getUser() != null || theProject.getAssignedDate() != null)
			return "access-denied";
		
		projectService.deleteById(projectId);
		return "redirect:/employees/projects";
	}
	
	@GetMapping("/assign")
	public String assignProject(@RequestParam("projectId") int projectId, Model theModel) {
		Project theProject = projectService.findById(projectId);
		if (theProject == null || theProject.getUser() != null || theProject.getAssignedDate() != null)
			return "access-denied";
		else {
			theModel.addAttribute("project", theProject);

			List<User> designers = userService.findAllDesigners();
			theModel.addAttribute("designers", designers);
			
			return "employees/projects/assign";
		}
	}
	
	@PostMapping("/assign")
	public String assignProject(@ModelAttribute("project") Project theProject, @RequestParam("projectId") int projectId) {
		Project p = projectService.findById(projectId);

		p.setUser(theProject.getUser());
		p.setAssignedDate(LocalDate.now());
		projectService.save(p);
		return "redirect:/employees/projects";
	}
	
	@GetMapping("/submit")
	public String submitProject(@RequestParam("projectId") int projectId) {
		Project theProject = projectService.findById(projectId);
		
		if (theProject == null || theProject.getUser() == null || theProject.getAssignedDate() == null ||
				theProject.getStatus().equals("Completed") || theProject.getSubmittedDate() != null || 
				theProject.getStatus().equals("Cancelled") || theProject.getCancelledDate() != null)
			return "access-denied";
		
		theProject.setStatus("Completed");
		theProject.setSubmittedDate(LocalDate.now());
		projectService.save(theProject);
		return "redirect:/employees/projects";
	}
	
	@GetMapping("/undoComplete")
	public String undoCompleteProject(@RequestParam("projectId") int projectId) {
		Project theProject = projectService.findById(projectId);
		
		if (theProject == null || !theProject.getStatus().equals("Completed") || theProject.getSubmittedDate() == null)
			return "access-denied";
		
		theProject.setStatus("N/A");
		theProject.setSubmittedDate(null);
		projectService.save(theProject);
		return "redirect:/employees/projects";
	}
	
	@GetMapping("/undoCancel")
	public String undoCancelledProject(@RequestParam("projectId") int theId) {
		Project theProject = projectService.findById(theId);
		
		if (theProject == null || !theProject.getStatus().equals("Cancelled") || theProject.getCancelledDate() == null)
			return "access-denied";
		
		theProject.setStatus("N/A");
		theProject.setCancelledDate(null);
		projectService.save(theProject);
		return "redirect:/employees/projects";
	}
	
}
