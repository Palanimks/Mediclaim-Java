package com.hcl.mediclaim.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hcl.mediclaim.entity.Role;


public interface RoleRepository extends JpaRepository<Role, Integer> {

}
