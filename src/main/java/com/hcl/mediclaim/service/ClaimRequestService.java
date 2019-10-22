package com.hcl.mediclaim.service;

import com.hcl.mediclaim.dto.ApproveClaimRequestDto;
import com.hcl.mediclaim.dto.ApproveClaimResponseDto;
import com.hcl.mediclaim.dto.MedicalClaimRequestDto;
import com.hcl.mediclaim.dto.MedicalClaimResponseDto;
import com.hcl.mediclaim.exception.InvalidClaimIdException;
import com.hcl.mediclaim.exception.InvalidPolicyIdException;
import com.hcl.mediclaim.exception.InvalidUserException;
import com.hcl.mediclaim.exception.RemarksEmptyException;

/**
 * 
 * @author Sushil
 *
 */
public interface ClaimRequestService {
	
	public MedicalClaimResponseDto applyMedicalClaim(MedicalClaimRequestDto medicalClaimRequestDto) throws InvalidUserException, InvalidPolicyIdException;
	
	public ApproveClaimResponseDto approveMedicalClaim(int claimId,ApproveClaimRequestDto claimRequestDto) throws InvalidClaimIdException, InvalidUserException, RemarksEmptyException;

}
