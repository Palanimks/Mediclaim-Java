package com.hcl.mediclaim.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hcl.mediclaim.dto.MedicalClaimRequestDto;
import com.hcl.mediclaim.dto.MedicalClaimResponseDto;
import com.hcl.mediclaim.exception.InvalidPolicyIdException;
import com.hcl.mediclaim.exception.InvalidUserException;
import com.hcl.mediclaim.service.ClaimRequestService;

import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author Sushil
 *
 */

@Slf4j
@RequestMapping("/")
@RestController
@CrossOrigin(allowedHeaders = { "*", "*/" }, origins = { "*", "*/" })
public class MedicalClaimController {
	
	@Autowired
	ClaimRequestService claimRequestService;
	/**
	 * This method is use to apply medical claim for user 
	 * @param medicalClaimRequestDto ,not null
	 * @return MedicalClaimResponseDto , not null
	 * @throws InvalidUserException     if user does not exist
	 * @throws InvalidPolicyIdException if policy id does not exist
	 */
	
	@PostMapping("/claims/users")
	public ResponseEntity<MedicalClaimResponseDto> applyMedicalClaim(@RequestBody MedicalClaimRequestDto medicalClaimRequestDto) throws InvalidUserException, InvalidPolicyIdException
	{
		log.info("Inside applyMedicalClaim of MedicalClaimController");
		MedicalClaimResponseDto response  = claimRequestService.applyMedicalClaim(medicalClaimRequestDto);
		
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
	

}
