package com.spaceage.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spaceage.model.Bom;

@Repository
public interface BomRepository extends JpaRepository<Bom, Integer>{

}
