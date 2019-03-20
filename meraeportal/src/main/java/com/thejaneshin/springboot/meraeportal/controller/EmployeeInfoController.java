package com.thejaneshin.springboot.meraeportal.controller;

import java.security.SecureRandom;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thejaneshin.springboot.meraeportal.entity.User;
import com.thejaneshin.springboot.meraeportal.service.RoleService;
import com.thejaneshin.springboot.meraeportal.service.UserService;

@Controller
@RequestMapping("/employees/info")
public class EmployeeInfoController {
	private UserService userService;
	private RoleService roleService;
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	// Characters to choose from for randomly generated password
	private static final String dict = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	private static SecureRandom rand = new SecureRandom();
	
	public EmployeeInfoController(UserService theUserService, RoleService theRoleService, BCryptPasswordEncoder bCrypt) {
		userService = theUserService;
		roleService = theRoleService;
		bCryptPasswordEncoder = bCrypt;
	}
	
	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
		dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
	}
	
	@GetMapping("")
	public String listProjects(Model theModel) {
		List<User> admins = userService.findAllAdminsOrdered();
		theModel.addAttribute("admins", admins);
		
		List<User> justDesigners = userService.findAllJustDesignersOrdered();
		theModel.addAttribute("justDesigners", justDesigners);
		
		return "employees/info/index";
	}
	
	@GetMapping("/add")
	public String addUser(Model theModel) {
		User user = new User();
		theModel.addAttribute("user", user);
		
		return "employees/info/add";
	}
	
	@GetMapping("/update")
	public String updateUser(@RequestParam("userId") int userId, Model theModel) {
		User user = userService.findById(userId);
		
		// If no such user or the user has an admin role
		if (user == null || roleService.findAllRolesByUserId(userId).contains("Admin"))
			return "access-denied";	
		theModel.addAttribute("user", user);
		
		return "employees/info/add";
	}
	
	@PostMapping("/save")
	public String saveUser(Model theModel, @Valid @ModelAttribute("user") User theUser, BindingResult theBindingResult,
			RedirectAttributes redirAttrs) {

		User existingUser = userService.findById(theUser.getId());
		
		// When creating new account, if username already exists, regardless of capitalization
		if (existingUser == null) {
			for (String name : userService.findAllUsernames()) {
				if (theUser.getUsername().equalsIgnoreCase(name)) {
					theModel.addAttribute("isExist", true);
					return "employees/info/add";
				}
			}
		}
		
		// If no roles are selected
		if (theUser.getRoles().size() == 0) {
			theModel.addAttribute("noRoles", true);
			return "employees/info/add";
		}		
		
		if (theBindingResult.hasErrors())
			return "employees/info/add";

		// Ensures both first and last names are capitalized correctly
		String correctFirst = theUser.getFirstName();
		correctFirst = correctFirst.substring(0, 1).toUpperCase() + correctFirst.substring(1).toLowerCase();
		theUser.setFirstName(correctFirst);
		
		String correctLast = theUser.getLastName();
		correctLast = correctLast.substring(0, 1).toUpperCase() + correctLast.substring(1).toLowerCase();
		theUser.setLastName(correctLast);
		
		// If adding a new user, generate random password and enable user account
		// Display the user info and the new generated password
		if (existingUser == null) {
			String randomPass = generateRandomPass(10);
			theUser.setPassword(bCryptPasswordEncoder.encode(randomPass)); 
			theUser.setEnabled(1);
			redirAttrs.addFlashAttribute("showNewPass", true);
			redirAttrs.addFlashAttribute("newName", theUser.getFirstName() + " " + theUser.getLastName());
			redirAttrs.addFlashAttribute("newUser", theUser.getUsername());
			redirAttrs.addFlashAttribute("newPass", randomPass);
		}
		
		userService.save(theUser);
		return "redirect:/employees/info";
	}
	
	@GetMapping("/resetPassword")
	public String resetPassword(@RequestParam("userId") int userId, RedirectAttributes redirAttrs) {
		User user = userService.findById(userId);
		
		if (user == null || roleService.findAllRolesByUserId(userId).contains("Admin"))
			return "access-denied";	
		
		// Generate new random password and then display it
		String randomPass = generateRandomPass(10);
		user.setPassword(bCryptPasswordEncoder.encode(randomPass)); 
		redirAttrs.addFlashAttribute("resetPass", true);
		redirAttrs.addFlashAttribute("name", user.getFirstName() + " " + user.getLastName());
		redirAttrs.addFlashAttribute("user", user.getUsername());
		redirAttrs.addFlashAttribute("pass", randomPass);
		
		userService.save(user);
		return "redirect:/employees/info";
	}
	
	private String generateRandomPass(int length) {
		StringBuilder sb = new StringBuilder(length);
		for (int i = 0; i < length; i++) 
			sb.append(dict.charAt(rand.nextInt(dict.length())));
			
		return sb.toString(); 
	}
}
