package com.thejaneshin.springboot.meraeportal.dao;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.thejaneshin.springboot.meraeportal.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	@Query("SELECT p.user FROM Project p WHERE p.id=:projectId")
	public User findByProjectId(int projectId);
	
	public User findByUsername(String username);
	
	@Query("SELECT u FROM User u ORDER BY u.lastName ASC, u.firstName ASC")
	public List<User> findAllByNameOrder();
	
	public User findById(int userId);
}
