package com.thejaneshin.springboot.meraeportal.service;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.thejaneshin.springboot.meraeportal.dao.UserRepository;
import com.thejaneshin.springboot.meraeportal.entity.Role;
import com.thejaneshin.springboot.meraeportal.entity.User;

@Service
public class UserServiceImpl implements UserService {
	private UserRepository userRepository;
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	public UserServiceImpl(UserRepository theUserRepository, BCryptPasswordEncoder theBCrypt) {
		userRepository = theUserRepository;
		bCryptPasswordEncoder = theBCrypt;
	}
	
	@Override
	public User getCurrentUser() {
		if (SecurityContextHolder.getContext() == null || SecurityContextHolder.getContext().getAuthentication() == null)
            return null;
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = userDetails.getUsername();
		User user = userRepository.findByUsername(username);
		return user;
	}

	@Override
	public User findUserByProjectId(int projectId) {
		return userRepository.findByProjectId(projectId);
	}

	@Override
	public List<User> findAllDesigners() {
		List<User> designers = new LinkedList<>();
		
		// So the list of designers is in name alphabetical order
		for (User u : userRepository.findAllByNameOrder()) {
			for (Role r : u.getRoles()) {
				if (r.getName().equals("ROLE_DESIGNER"))
					designers.add(u);
			}
		}
		
		return designers;
	}

	@Override
	public List<String> findAllRolesByUserId(int userId) {
		User user = userRepository.findById(userId);
		List<String> roles = new LinkedList<>();
		for (Role r : user.getRoles())
			roles.add(r.getName());
		
		// List of role names sorted by order of hierarchy
		List<String> orderedRoles = new LinkedList<>();
		
		if (roles.contains("ROLE_ADMIN"))
			orderedRoles.add("Admin");
		if (roles.contains("ROLE_DESIGNER"))
			orderedRoles.add("Designer");
		
		return orderedRoles;
	}

	@Override
	public User findById(int userId) {
		return userRepository.findById(userId);
	}
	
	@Override
	public void save(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		userRepository.save(user);
	}

}
