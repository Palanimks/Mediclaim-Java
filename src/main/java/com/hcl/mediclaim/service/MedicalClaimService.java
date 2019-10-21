package com.hcl.mediclaim.service;

import java.util.List;

import com.hcl.mediclaim.dto.ClaimDetailsDto;
import com.hcl.mediclaim.dto.ClaimDto;

public interface MedicalClaimService {

	List<ClaimDto> getClaims(Integer userId);

	ClaimDetailsDto getClainDetails(Integer claimId);

}
