package com.hcl.mediclaim.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.hcl.mediclaim.dto.MedicalClaimRequestDto;
import com.hcl.mediclaim.dto.MedicalClaimResponseDto;
import com.hcl.mediclaim.entity.ClaimRequest;
import com.hcl.mediclaim.entity.Policy;
import com.hcl.mediclaim.entity.User;
import com.hcl.mediclaim.exception.InvalidPolicyIdException;
import com.hcl.mediclaim.exception.InvalidUserException;
import com.hcl.mediclaim.repository.ClaimRequestRepository;
import com.hcl.mediclaim.repository.PolicyRepository;
import com.hcl.mediclaim.repository.UserPolicyRepository;
import com.hcl.mediclaim.repository.UserRepository;

/**
 * This class is use to write unit test cases for ClaimRequestServiceImpl class
 * 
 * @author Sushil
 * 
 */
@RunWith(MockitoJUnitRunner.Silent.class)
public class TestClaimRequestServiceImpl {

	@InjectMocks
	ClaimRequestServiceImpl claimRequestServiceImpl;

	@Mock
	UserPolicyRepository userPolicyRepository;

	@Mock
	PolicyRepository policyRepository;

	@Mock
	UserRepository userRepository;

	@Mock
	ClaimRequestRepository claimRequestRepository;

	User user;
	Policy policy;

	MedicalClaimRequestDto medicalClaimRequestDto = null;
	MedicalClaimRequestDto medicalClaimRequestDto1 = null;
	ClaimRequest claimRequest = null;

	@Before
	public void setup() {
		user = new User();
		user.setUserId(101);
		user.setRoleId(1);
		user.setUserName("raja");
		user.setAadhaarNumber(111122223333l);

		policy = new Policy(1, 1111, "policy1", "kidney", 50000);

		claimRequest = new ClaimRequest() ; 
		claimRequest.setClaimId(101);
		claimRequest.setPolicyId(101);	
   
		medicalClaimRequestDto = new MedicalClaimRequestDto();
		medicalClaimRequestDto.setAadhaarNumber(11111111L);
		medicalClaimRequestDto.setAdmissionDate(LocalDate.now().minusDays(20));
		medicalClaimRequestDto.setPolicyId(1010);
		medicalClaimRequestDto.setUserId(101);
		medicalClaimRequestDto.setHospitalName("Fortis");

		medicalClaimRequestDto1 = new MedicalClaimRequestDto();
		medicalClaimRequestDto1.setAadhaarNumber(111122223333l);
		medicalClaimRequestDto1.setAdmissionDate(LocalDate.now().minusDays(20));
		medicalClaimRequestDto1.setPolicyId(1);
		medicalClaimRequestDto1.setUserId(101);
		medicalClaimRequestDto1.setHospitalName("Fortis");
	}

	 /**
	  * This unit test case use to  test positive case
	  * @throws InvalidUserException if user id does not match
	  * @throws InvalidPolicyIdException if policy id does not match
	  */
	  @Test 
	  public void testApplyMedicalClaimPositive() throws
	  InvalidUserException, InvalidPolicyIdException {
	  Mockito.when(userRepository.findById(101)).thenReturn(Optional.of(user));
	  Mockito.when(policyRepository.findById(1)).thenReturn(Optional.of(policy));
	  
	  Mockito.when(claimRequestRepository.save(Mockito.any())).thenReturn(
	  claimRequest);
	  
	  MedicalClaimResponseDto response =
	  claimRequestServiceImpl.applyMedicalClaim(medicalClaimRequestDto1);
	  
	  assertEquals(101, response.getClaimId());
	  
	  
	  }
	 
    /**
     * This unit test is use to validate user id  
     * @throws InvalidUserException
     */
	@Test(expected = InvalidUserException.class)
	public void testApplyMedicalClaimUserIdNegative() throws InvalidUserException, InvalidPolicyIdException {
		medicalClaimRequestDto.setUserId(0);
		Mockito.when(userRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(user));
		Mockito.when(policyRepository.findById(1)).thenReturn(Optional.of(policy));

		Mockito.when(claimRequestRepository.save(claimRequest)).thenReturn(claimRequest);

		MedicalClaimResponseDto response = claimRequestServiceImpl.applyMedicalClaim(medicalClaimRequestDto);

		assertEquals(101, response.getClaimId());

	}
	/**
	 * This unit test use to validate aadhar number
	 * @throws InvalidUserException if aadhar number does not match
	 * 
	 */
	@Test(expected = InvalidUserException.class)
	public void testApplyMedicalClaimAadharNumberNegative() throws InvalidUserException, InvalidPolicyIdException {
		user.setUserId(0);
		Mockito.when(userRepository.findById(101)).thenReturn(Optional.of(user));
		Mockito.when(policyRepository.findById(1)).thenReturn(Optional.of(policy));

		Mockito.when(claimRequestRepository.save(claimRequest)).thenReturn(claimRequest);

		MedicalClaimResponseDto response = claimRequestServiceImpl.applyMedicalClaim(medicalClaimRequestDto);

		assertEquals(101, response.getClaimId());

	}
	/**
	 * This unit test use to validate policy id 
	 * @throws InvalidPolicyIdException if policy id does not match
	 */
	@Test(expected = InvalidPolicyIdException.class)
	public void testApplyMedicalClaimPolicyIdNegative() throws InvalidUserException, InvalidPolicyIdException {
		medicalClaimRequestDto1.setPolicyId(0);
		Mockito.when(userRepository.findById(101)).thenReturn(Optional.of(user));
		Mockito.when(policyRepository.findById(1)).thenReturn(Optional.of(policy));

		Mockito.when(claimRequestRepository.save(Mockito.any())).thenReturn(claimRequest);

		MedicalClaimResponseDto response = claimRequestServiceImpl.applyMedicalClaim(medicalClaimRequestDto1);

		assertEquals(101, response.getClaimId());

	}

}
