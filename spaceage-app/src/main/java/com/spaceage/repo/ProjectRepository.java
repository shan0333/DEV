package com.spaceage.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spaceage.model.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer>{

}
