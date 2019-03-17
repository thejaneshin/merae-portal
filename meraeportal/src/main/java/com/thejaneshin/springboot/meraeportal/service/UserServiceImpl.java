package com.thejaneshin.springboot.meraeportal.service;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.thejaneshin.springboot.meraeportal.dao.UserRepository;
import com.thejaneshin.springboot.meraeportal.entity.Role;
import com.thejaneshin.springboot.meraeportal.entity.User;

@Service
public class UserServiceImpl implements UserService {
	private UserRepository userRepository;
	
	@Autowired
	public UserServiceImpl(UserRepository theUserRepository) {
		userRepository = theUserRepository;
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

}
