package com.thejaneshin.springboot.meraeportal.controller;

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

import com.thejaneshin.springboot.meraeportal.entity.User;
import com.thejaneshin.springboot.meraeportal.service.UserService;

@Controller
@RequestMapping("/settings")
public class ProfileController {
	private UserService userService;
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public ProfileController(UserService theUserService, BCryptPasswordEncoder thebCrypt) {
		userService = theUserService;
		bCryptPasswordEncoder = thebCrypt;
	}
	
	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
		dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
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
	public String editProfile(Model theModel, @Valid @ModelAttribute("me") User me, BindingResult theBindingResult) {
		if (theBindingResult.hasErrors())
			return "settings/edit";
		
		User u = userService.findById(me.getId());
		u.setEmail(me.getEmail());
		u.setPhone(me.getPhone());
		userService.save(u);
		theModel.addAttribute("successChange", true);
		
		return "settings/edit";
	}
	
	@GetMapping("/changePassword")
	public String resetPassword(Model theModel) {
		User me = userService.getCurrentUser();
		theModel.addAttribute("me", me);
		
		return "settings/changePassword";
	}
	
	@PostMapping("/changePassword")
	public String resetPassword(Model theModel, @Valid @ModelAttribute("me") User me,
			@RequestParam("currPass") String currPass, @RequestParam(value="newPass", required=false) String newPass,
			@RequestParam("verifyPass") String verifyPass) {
		
		User u = userService.findById(me.getId());
		
		if (currPass == null || currPass.isEmpty()) {
			theModel.addAttribute("isCurrEmpty", true);
			return "settings/changePassword";
		}
		
		if (!bCryptPasswordEncoder.matches(currPass, u.getPassword())) {
			theModel.addAttribute("isWrongPassword", true);
			return "settings/changePassword";
		}
		
		if ((newPass == null || newPass.isEmpty()) && (verifyPass == null || verifyPass.isEmpty())) {
			theModel.addAttribute("isNewEmpty", true);
			theModel.addAttribute("isVerifyEmpty", true);
			return "settings/changePassword";
		}
		
		if (newPass == null || newPass.isEmpty()) {
			theModel.addAttribute("isNewEmpty", true);
			return "settings/changePassword";
		}
		
		if (verifyPass == null || verifyPass.isEmpty()) {
			theModel.addAttribute("isVerifyEmpty", true);
			return "settings/changePassword";
		}
		
		if (!newPass.equals(verifyPass)) {
			theModel.addAttribute("isNotMatch", true);
			return "settings/changePassword";
		}
		
		u.setPassword(newPass);
		userService.save(u);
		theModel.addAttribute("successChange", true);
		
		return "settings/changePassword";
	}
}
