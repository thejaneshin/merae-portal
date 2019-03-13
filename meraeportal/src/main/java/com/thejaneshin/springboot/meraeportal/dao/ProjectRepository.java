package com.thejaneshin.springboot.meraeportal.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.thejaneshin.springboot.meraeportal.entity.Project;

public interface ProjectRepository extends JpaRepository<Project, Integer> {
	// Going have to fix this if I fix Project's GeneratedValue strategy
	public List<Project> findByUserIsNullOrderByIdDesc();
	
	@Query("SELECT p FROM Project p WHERE p.user.id=:id AND p.submittedDate IS NULL ORDER BY p.dueDate ASC")
	public List<Project> findByUserIncomplete(int id);
	
	@Query("SELECT p FROM Project p WHERE p.user.id=:id AND p.submittedDate IS NOT NULL AND p.status LIKE 'Completed'"
			+ " ORDER BY p.submittedDate ASC")
	public List<Project> findByUserCompleted(int id);
	
}
