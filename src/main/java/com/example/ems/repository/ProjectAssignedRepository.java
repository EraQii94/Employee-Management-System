package com.example.ems.repository;

import com.example.ems.entity.ProjectAssigned;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProjectAssignedRepository extends JpaRepository<ProjectAssigned, Long> {
	List<ProjectAssigned> findByProjectId(Long projectId);

	List<ProjectAssigned> findByEmployeeId(Long employeeId);

}
