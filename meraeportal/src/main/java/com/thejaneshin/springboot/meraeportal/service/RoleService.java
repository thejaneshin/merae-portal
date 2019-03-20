package com.thejaneshin.springboot.meraeportal.service;

import java.util.List;

import com.thejaneshin.springboot.meraeportal.entity.Role;

public interface RoleService {
	public List<String> findAllRolesByUserId(int userId);
	
	public Role findByName(String name);
	
}
