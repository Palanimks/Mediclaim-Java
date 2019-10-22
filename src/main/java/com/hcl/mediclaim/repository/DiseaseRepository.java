package com.hcl.mediclaim.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hcl.mediclaim.entity.Disease;

/**
 * Disease Repository interface
 * 
 * @author Laxman
 * @date 21-OCT-2019
 * 
 */
public interface DiseaseRepository extends JpaRepository<Disease, Integer> {

}
