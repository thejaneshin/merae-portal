package com.thejaneshin.springboot.meraeportal.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.thejaneshin.springboot.meraeportal.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	public User findByUsername(String username);
}
