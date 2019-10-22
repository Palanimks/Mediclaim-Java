package com.hcl.mediclaim.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hcl.mediclaim.entity.UserPolicy;
/**
 * 
 * @author Sushil
 *
 */
@Repository
public interface UserPolicyRepository extends JpaRepository<UserPolicy, Integer> {
	
	Optional<UserPolicy> findByUserIdAndPolicyId(int userId,int policyId);

}
