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
 * 
 * @author Sushil / Laxman
 *
 */

@Slf4j
@RequestMapping("/")
@RestController
@CrossOrigin(allowedHeaders = { "*", "*/" }, origins = { "*", "*/" })
public class MedicalClaimController {
	
	@Autowired
	private MedicalClaimService medicalClaimService;
	
	@GetMapping("/claims/users/{userId}")
	public ResponseEntity<List<ClaimDto>> getPolicies(@PathVariable Integer userId){
		return new ResponseEntity<>(medicalClaimService.getClaims(userId), HttpStatus.OK);
	}

	@GetMapping("/claims/{claimId}")
	public ResponseEntity<ClaimDetailsDto> getClaimDetails(@PathVariable Integer claimId){
		return new ResponseEntity<>(medicalClaimService.getClainDetails(claimId), HttpStatus.OK);
	}
}
