package com.thejaneshin.springboot.meraeportal.service;

import java.util.List;

import com.thejaneshin.springboot.meraeportal.entity.User;

public interface UserService {
	public User getCurrentUser();
	
	public User findUserByProjectId(int projectId);
	
	public List<User> findAllDesigners();
	
	public List<User> findAllAdminsOrdered();
	
	public List<User> findAllJustDesignersOrdered();
	
	public User findById(int userId);
	
	public User findByUsername(String username);
	
	public List<String> findAllUsernames();
	
	public void save(User user);
}
