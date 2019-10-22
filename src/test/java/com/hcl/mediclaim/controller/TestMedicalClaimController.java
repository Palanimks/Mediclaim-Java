package com.hcl.mediclaim.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

import com.hcl.mediclaim.dto.ApproveClaimRequestDto;
import com.hcl.mediclaim.dto.ApproveClaimResponseDto;
import com.hcl.mediclaim.dto.ClaimDetailsDto;
import com.hcl.mediclaim.dto.ClaimDto;
import com.hcl.mediclaim.dto.MedicalClaimResponseDto;
import com.hcl.mediclaim.exception.InvalidClaimIdException;
import com.hcl.mediclaim.exception.InvalidPolicyIdException;
import com.hcl.mediclaim.exception.InvalidUserException;
import com.hcl.mediclaim.exception.RemarksEmptyException;
import com.hcl.mediclaim.service.ClaimRequestServiceImpl;
import com.hcl.mediclaim.service.MedicalClaimServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class TestMedicalClaimController {
	
	@InjectMocks
	MedicalClaimController medicalClaimController;
	
	@Mock
	MedicalClaimServiceImpl medicalClaimServiceImpl;
	
	@Mock
	ClaimRequestServiceImpl claimRequestServiceImpl;
	
	MedicalClaimResponseDto medicalClaimResponseDto;
	
	List<ClaimDto> listClaim = null;
	
	ClaimDetailsDto claimDetailsDto;
	
	ClaimDto claimDto;
	
	ApproveClaimRequestDto approveClaimRequestDto =null;
	ApproveClaimResponseDto approveClaimResponseDto = null;
	
	@Before
	public void setup()
	{
		medicalClaimResponseDto = new MedicalClaimResponseDto("claim has submitted", "success", 1);
		
		claimDetailsDto = new ClaimDetailsDto();
		claimDetailsDto.setClaimId(101);
		claimDetailsDto.setApproverId(1);
		claimDetailsDto.setPolicyId(1);
		claimDetailsDto.setStatus("approve");
		claimDetailsDto.setUserName("raja");
		
		claimDto = new ClaimDto();
		claimDto.setClaimAmount(1000.0);
		claimDto.setClaimId(1);
		claimDto.setPolicyId(1);
		claimDto.setUserName("raja");
		
		listClaim = new ArrayList<ClaimDto>();
		listClaim.add(claimDto);
		
		approveClaimRequestDto =new ApproveClaimRequestDto();
		approveClaimRequestDto.setStatus("APPROVED");
		approveClaimRequestDto.setUserId(102);
		
		approveClaimResponseDto = new ApproveClaimResponseDto();
		approveClaimResponseDto.setMessage("approdev successfully");
		approveClaimResponseDto.setStatus("Success");
	}
	
	
	@Test
	public void testApplyMedicalClaim() throws InvalidUserException, InvalidPolicyIdException {
		
		Mockito.when(claimRequestServiceImpl.applyMedicalClaim(Mockito.any())).thenReturn(medicalClaimResponseDto);
		ResponseEntity<MedicalClaimResponseDto> response = medicalClaimController.applyMedicalClaim(Mockito.any());
		
		assertEquals(201, response.getStatusCodeValue());
	}
	
	@Test
	public void testGetClaims()
	{
		Mockito.when(medicalClaimServiceImpl.getClaims(Mockito.anyInt())).thenReturn(listClaim);
		ResponseEntity<List<ClaimDto>> response  = medicalClaimController.getClaims(Mockito.anyInt());
		
		assertEquals(200, response.getStatusCodeValue());
	}
	
	@Test
	public void testGetClaimDetails()
	{
		Mockito.when(medicalClaimServiceImpl.getClainDetails(Mockito.any())).thenReturn(claimDetailsDto);
		ResponseEntity<ClaimDetailsDto> response = medicalClaimController.getClaimDetails(Mockito.anyInt());
		
		assertEquals(200, response.getStatusCode().value());
	}

	@Test
	public void testApproveClaim() throws InvalidClaimIdException, InvalidUserException, RemarksEmptyException {
		Mockito.when(claimRequestServiceImpl.approveMedicalClaim(1010, approveClaimRequestDto)).thenReturn(approveClaimResponseDto);
		ResponseEntity<ApproveClaimResponseDto> actualResult = medicalClaimController.approveClaim(1010, approveClaimRequestDto);
		assertEquals("Success", actualResult.getBody().getStatus());
	}
}
