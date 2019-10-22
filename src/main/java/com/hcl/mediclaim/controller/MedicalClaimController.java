package com.hcl.mediclaim.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hcl.mediclaim.dto.ApproveClaimRequestDto;
import com.hcl.mediclaim.dto.ApproveClaimResponseDto;
import com.hcl.mediclaim.dto.ClaimDetailsDto;
import com.hcl.mediclaim.dto.ClaimDto;
import com.hcl.mediclaim.dto.MedicalClaimRequestDto;
import com.hcl.mediclaim.dto.MedicalClaimResponseDto;
import com.hcl.mediclaim.exception.InvalidClaimIdException;
import com.hcl.mediclaim.exception.InvalidPolicyIdException;
import com.hcl.mediclaim.exception.InvalidUserException;
import com.hcl.mediclaim.exception.RemarksEmptyException;
import com.hcl.mediclaim.service.ClaimRequestService;
import com.hcl.mediclaim.service.MedicalClaimService;

import lombok.extern.slf4j.Slf4j;

/**
 * Controller to perform claim
 * 
 * @author Laxman /Sushil
 * @date 21-OCT-2019
 *
 */

@Slf4j
@RequestMapping("/")
@RestController
@CrossOrigin(allowedHeaders = { "*", "*/" }, origins = { "*", "*/" })
public class MedicalClaimController {

	@Autowired
	private ClaimRequestService claimRequestService;

	@Autowired
	private MedicalClaimService medicalClaimService;

	/**
	 * This method is use to apply medical claim for user
	 * 
	 * @param medicalClaimRequestDto ,not null
	 * @return MedicalClaimResponseDto , not null
	 * @throws InvalidUserException     if user does not exist
	 * @throws InvalidPolicyIdException if policy id does not exist
	 */
	@PostMapping("/claims/users")
	public ResponseEntity<MedicalClaimResponseDto> applyMedicalClaim(
			@RequestBody MedicalClaimRequestDto medicalClaimRequestDto)
			throws InvalidUserException, InvalidPolicyIdException {
		log.info("Inside applyMedicalClaim of MedicalClaimController");
		MedicalClaimResponseDto response = claimRequestService.applyMedicalClaim(medicalClaimRequestDto);

		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

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

	@PutMapping("/claims/{claimId}")
	public ResponseEntity<ApproveClaimResponseDto> approveClaim(@PathVariable Integer claimId,
			@RequestBody ApproveClaimRequestDto approveClaimRequestDto)
			throws InvalidClaimIdException, InvalidUserException, RemarksEmptyException {
		return new ResponseEntity<>(claimRequestService.approveMedicalClaim(claimId, approveClaimRequestDto),
				HttpStatus.OK);
	}
}
