package com.thejaneshin.springboot.meraeportal.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.thejaneshin.springboot.meraeportal.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	public User findByUsername(String username);
	
	@Query("SELECT p.user FROM Project p WHERE p.id=:projectId")
	public User findByProjectId(int projectId);
}
