package com.thejaneshin.springboot.meraeportal.service;

import java.util.List;

import com.thejaneshin.springboot.meraeportal.entity.User;

public interface UserService {
	public User getCurrentUser();
	
	public User findUserByProjectId(int projectId);
	
	public List<User> findAllDesigners();
	
	public List<String> findAllRolesByUserId(int userId);
	
	public User findById(int userId);
	
	public void save(User user);
}
