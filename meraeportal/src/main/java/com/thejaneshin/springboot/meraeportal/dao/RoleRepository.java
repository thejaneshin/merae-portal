package com.thejaneshin.springboot.meraeportal.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.thejaneshin.springboot.meraeportal.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {
	public Role findByName(String name);

	@Query("SELECT u.roles FROM User u WHERE u.id=:userId")
	public List<Role> findAllByUserId(int userId);
}
