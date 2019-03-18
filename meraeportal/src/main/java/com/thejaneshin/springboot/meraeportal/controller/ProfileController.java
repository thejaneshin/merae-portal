package com.thejaneshin.springboot.meraeportal.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.thejaneshin.springboot.meraeportal.entity.User;
import com.thejaneshin.springboot.meraeportal.service.UserService;

@Controller
@RequestMapping("/settings")
public class ProfileController {
	private UserService userService;
	
	public ProfileController(UserService theUserService) {
		userService = theUserService;
	}
	
	@GetMapping("")
	public String showProfile(Model theModel) {
		User me = userService.getCurrentUser();
		theModel.addAttribute("me", me);
		
		List<String> myRoles = userService.findAllRolesByUserId(me.getId());
		theModel.addAttribute("myRoles", myRoles);
		
		return "settings/index";
	}
	
	@GetMapping("/edit")
	public String editProfile(Model theModel) {
		User me = userService.getCurrentUser();
		theModel.addAttribute("me", me);
		
		return "settings/edit";
	}
	
	@PostMapping("/edit")
	public String editProfile(@Valid @ModelAttribute("me") User me, BindingResult theBindingResult) {
		if (theBindingResult.hasErrors())
			return "settings/edit";
		
		User u = userService.findById(me.getId());
		u.setEmail(me.getEmail());
		u.setPhone(me.getPhone());
		userService.save(u);
		
		return "redirect:/settings";
	}
	
	@GetMapping("/resetPassword")
	public String resetPassword(Model theModel) {
		User me = userService.getCurrentUser();
		theModel.addAttribute("me", me);
		
		return "settings/resetPassword";
	}
}
