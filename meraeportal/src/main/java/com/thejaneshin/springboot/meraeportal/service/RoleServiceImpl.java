package com.thejaneshin.springboot.meraeportal.service;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thejaneshin.springboot.meraeportal.dao.RoleRepository;
import com.thejaneshin.springboot.meraeportal.entity.Role;

@Service
public class RoleServiceImpl implements RoleService {
	private RoleRepository roleRepository;
	
	@Autowired
	public RoleServiceImpl(RoleRepository theRoleRepository) {
		roleRepository = theRoleRepository;
	}

	@Override
	public List<String> findAllRolesByUserId(int userId) {
		List<String> roleNames = new LinkedList<>();
		List<String> orderedRoleNames = new LinkedList<>();
		
		for (Role r : roleRepository.findAllByUserId(userId))
			roleNames.add(r.getName());
		
		// Order by role hierarchy
		if (roleNames.contains("ROLE_ADMIN"))
			orderedRoleNames.add("Admin");
		if (roleNames.contains("ROLE_DESIGNER"))
			orderedRoleNames.add("Designer");
			
		return orderedRoleNames;
	}
	
	@Override
	public Role findByName(String name) {
		return roleRepository.findByName(name);
	}

}
