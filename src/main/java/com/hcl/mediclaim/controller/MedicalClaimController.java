package com.hcl.mediclaim.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hcl.mediclaim.dto.ClaimDetailsDto;
import com.hcl.mediclaim.dto.ClaimDto;
import com.hcl.mediclaim.service.MedicalClaimService;

import lombok.extern.slf4j.Slf4j;

/**
 * Controller to perform claim
 * 
 * @author Laxman
 * @date 21-OCT-2019
 *
 */

@Slf4j
@RequestMapping("/")
@RestController
@CrossOrigin(allowedHeaders = { "*", "*/" }, origins = { "*", "*/" })
public class MedicalClaimController {

	@Autowired
	private MedicalClaimService medicalClaimService;

	/**
	 * Method will return the list of claim based on login, if APPROVER has login
	 * then record will show for approver level when SUPPER APPROVER will login
	 * record will show for SUPPER APPROVER
	 * 
	 * @param userId
	 * @return List<ClaimDto>
	 */
	@GetMapping("/claims/users/{userId}")
	public ResponseEntity<List<ClaimDto>> getClaims(@PathVariable Integer userId) {

		log.info(" :: getPolicies ---- userId : {}", userId);
		return new ResponseEntity<>(medicalClaimService.getClaims(userId), HttpStatus.OK);
	}

	/**
	 * Method show the details of particular claim based on claim id.
	 * 
	 * @param claimId
	 * @return ClaimDetailsDto
	 */
	@GetMapping("/claims/{claimId}")
	public ResponseEntity<ClaimDetailsDto> getClaimDetails(@PathVariable Integer claimId) {

		log.info(" :: getClaimDetails ---- claimId : ", claimId);
		return new ResponseEntity<>(medicalClaimService.getClainDetails(claimId), HttpStatus.OK);
	}
}
