package com.hcl.mediclaim.service;

import com.hcl.mediclaim.dto.MedicalClaimRequestDto;
import com.hcl.mediclaim.dto.MedicalClaimResponseDto;
import com.hcl.mediclaim.exception.InvalidPolicyIdException;
import com.hcl.mediclaim.exception.InvalidUserException;

/**
 * 
 * @author Sushil
 *
 */
public interface ClaimRequestService {
	
	public MedicalClaimResponseDto applyMedicalClaim(MedicalClaimRequestDto medicalClaimRequestDto) throws InvalidUserException, InvalidPolicyIdException;

}
