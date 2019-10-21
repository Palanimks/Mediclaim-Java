package com.hcl.mediclaim.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hcl.mediclaim.entity.Disease;

public interface DiseaseRepository extends JpaRepository<Disease, Integer> {

}
