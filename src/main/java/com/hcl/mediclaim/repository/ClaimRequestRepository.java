package com.hcl.mediclaim.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hcl.mediclaim.entity.ClaimRequest;

/**
 * @author Laxman
 * @date 21-Oct-2019
 * 
 * @desc Claim request repository
 */
@Repository
public interface ClaimRequestRepository extends JpaRepository<ClaimRequest, Integer> {

	List<ClaimRequest> findByStatus(String status);
}
