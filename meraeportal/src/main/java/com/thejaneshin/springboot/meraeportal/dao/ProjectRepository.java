package com.thejaneshin.springboot.meraeportal.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.thejaneshin.springboot.meraeportal.entity.Project;

public interface ProjectRepository extends JpaRepository<Project, Integer> {
	public List<Project> findByUserIsNullOrderByIdDesc();
	
	public List<Project> findByUserId(int id);
	
}
