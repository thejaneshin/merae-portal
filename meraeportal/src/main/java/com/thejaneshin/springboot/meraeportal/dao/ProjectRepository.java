package com.thejaneshin.springboot.meraeportal.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.thejaneshin.springboot.meraeportal.entity.Project;

public interface ProjectRepository extends JpaRepository<Project, Integer> {
	@Query("SELECT p FROM Project p WHERE p.user.id=:id AND p.submittedDate IS NULL AND p.status NOT LIKE 'Completed'"
			+ " AND p.status NOT LIKE 'Cancelled' ORDER BY p.dueDate ASC")
	public List<Project> findByUserIncomplete(int id);
	
	@Query("SELECT p FROM Project p WHERE p.user.id=:id AND p.submittedDate IS NOT NULL AND p.status LIKE 'Completed'"
			+ " ORDER BY p.submittedDate DESC")
	public List<Project> findByUserCompleted(int id);
	
	@Query("SELECT p FROM Project p WHERE p.user.id=:id AND p.cancelledDate IS NOT NULL AND p.status LIKE 'Cancelled'"
			+ " ORDER BY p.cancelledDate DESC")
	public List<Project> findByUserCancelled(int id);
	
	// Going have to fix this if I fix Project's GeneratedValue strategy
	public List<Project> findByUserIsNullOrderByIdDesc();
	
	@Query("SELECT p FROM Project p WHERE p.submittedDate IS NOT NULL AND p.status LIKE 'Completed' ORDER BY "
			+ "p.submittedDate DESC")
	public List<Project> findAllCompleted();
	
	@Query("SELECT p FROM Project p WHERE p.cancelledDate IS NOT NULL AND p.status LIKE 'Cancelled'")
	public List<Project> findAllCancelled();
}
