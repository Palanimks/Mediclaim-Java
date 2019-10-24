package com.hcl.mediclaim.service;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.hcl.mediclaim.dto.ApproveClaimRequestDto;
import com.hcl.mediclaim.dto.ApproveClaimResponseDto;
import com.hcl.mediclaim.dto.ClaimDetailsDto;
import com.hcl.mediclaim.dto.ClaimDto;
import com.hcl.mediclaim.entity.ClaimRequest;
import com.hcl.mediclaim.entity.Role;
import com.hcl.mediclaim.entity.User;
import com.hcl.mediclaim.entity.UserPolicy;
import com.hcl.mediclaim.exception.InvalidClaimIdException;
import com.hcl.mediclaim.exception.InvalidUserException;
import com.hcl.mediclaim.exception.RemarksEmptyException;
import com.hcl.mediclaim.exception.RoleNotExistException;
import com.hcl.mediclaim.exception.UserPolicyNotExistException;
import com.hcl.mediclaim.repository.ClaimRequestRepository;
import com.hcl.mediclaim.repository.RoleRepository;
import com.hcl.mediclaim.repository.UserPolicyRepository;
import com.hcl.mediclaim.repository.UserRepository;

@RunWith(MockitoJUnitRunner.class)
public class MedicalClainServiceTest {

	@Mock
	private ClaimRequestRepository claimRequestRepository;
	
	@Mock
	private UserRepository userRepository;
	
	@Mock
	private RoleRepository roleRepository;
	
	@Mock
	private UserPolicyRepository userPolicyRepository;
	
	@InjectMocks
	private MedicalClaimServiceImpl medicalClaimServiceImpl;
	
	@InjectMocks
	private ClaimRequestServiceImpl claimRequestServiceImpl;
	
	Optional<User> optionalUser = null;
	Optional<Role> optionalRole = null;
	Optional<User> optionalSuperUser = null;
	Optional<Role> optionalSuperRole = null;
	List<ClaimRequest> claimRequests = null;
	Optional<ClaimRequest> optionalClaimRequest = null;
	List<ClaimRequest> claimRequestsSuperPending = null;
	Optional<ClaimRequest> optionalClaimRequestSuperPending = null;
	ApproveClaimRequestDto claimRequestDto = null;
	ApproveClaimRequestDto suerClaimRequestDto = null;
	ApproveClaimRequestDto emptyRemarksClaimRequestDto = null;
	ApproveClaimRequestDto rejectedClaimRequestDto = null;
	
	Optional<User> optUser = null;
	Optional<UserPolicy> optionalUserPolicy = null;
	Optional<Role> optRole = null;
	
	@Before
	public void setUp() {
		
		User user = new User();
		user.setAadhaarNumber(122333333333L);
		user.setDateOfBirth(LocalDate.now());
		user.setRoleId(2);
		user.setUserId(10);
		user.setUserName("Laxman");
		optionalUser = Optional.of(user);
		
		Role role = new Role();
		role.setRoleId(2);
		role.setRoleName("ADMIN");
		optionalRole = Optional.of(role);
		
		ClaimRequest claimRequest =new ClaimRequest();
		claimRequest.setAdmissionDate(LocalDate.now().minusDays(20));
		claimRequest.setClaimAmount(1000.0);
		claimRequest.setClaimId(101);
		claimRequest.setDischargeDate(LocalDate.now().minusDays(2));
		claimRequest.setHospitalName("Fortis");
		claimRequest.setUserId(101);
		claimRequest.setPolicyId(1010);
		claimRequests = new ArrayList<>();
		claimRequests.add(claimRequest);
		optionalClaimRequest = Optional.of(claimRequest);
		
		ClaimRequest claimRequestSuperPending =new ClaimRequest();
		claimRequestSuperPending.setAdmissionDate(LocalDate.now().minusDays(20));
		claimRequestSuperPending.setClaimAmount(120000.0);
		claimRequestSuperPending.setClaimId(101);
		claimRequestSuperPending.setDischargeDate(LocalDate.now().minusDays(2));
		claimRequestSuperPending.setHospitalName("Fortis");
		claimRequestSuperPending.setUserId(101);
		claimRequestSuperPending.setPolicyId(1010);
		claimRequestsSuperPending = new ArrayList<>();
		claimRequestsSuperPending.add(claimRequest);
		optionalClaimRequestSuperPending = Optional.of(claimRequestSuperPending);
		
		claimRequestDto = new ApproveClaimRequestDto();
		claimRequestDto.setRemarks("Approved.");
		claimRequestDto.setStatus("APPROVED");
		claimRequestDto.setUserId(10);
		
		optUser = Optional.of(user);
		
		UserPolicy userPolicy = new UserPolicy();
		userPolicy.setBalanceAmount(100000);
		userPolicy.setExpiryDate(LocalDate.now().plusYears(2));
		userPolicy.setIssuedDate(LocalDate.now().minusMonths(3));
		userPolicy.setPolicyId(1010);
		userPolicy.setUserId(101);
		userPolicy.setUserPolicyId(1);
		optionalUserPolicy = Optional.of(userPolicy);
		
		
		// -------------------------------------------------
		User superuser = new User();
		superuser.setAadhaarNumber(122333333333L);
		superuser.setDateOfBirth(LocalDate.now());
		superuser.setRoleId(3);
		superuser.setUserId(11);
		superuser.setUserName("Laxman");
		optionalSuperUser = Optional.of(superuser);
		
		Role superRole = new Role();
		superRole.setRoleId(3);
		superRole.setRoleName("SUPER_ADMIN");
		optionalSuperRole = Optional.of(superRole);
		
		suerClaimRequestDto = new ApproveClaimRequestDto();
		suerClaimRequestDto.setRemarks("Approved.");
		suerClaimRequestDto.setStatus("APPROVED");
		suerClaimRequestDto.setUserId(11);
		
		// ----------------------------------------------
		emptyRemarksClaimRequestDto = new ApproveClaimRequestDto();
		emptyRemarksClaimRequestDto.setRemarks("");
		emptyRemarksClaimRequestDto.setStatus("REJECTED");
		emptyRemarksClaimRequestDto.setUserId(10);
		
		// ---------------------------------------------
		rejectedClaimRequestDto = new ApproveClaimRequestDto();
		rejectedClaimRequestDto.setRemarks("Rejected ");
		rejectedClaimRequestDto.setStatus("REJECTED");
		rejectedClaimRequestDto.setUserId(10);
	}
	
	@Test
	public void testGetClaims() {
		Mockito.when(userRepository.findById(10)).thenReturn(optionalUser);
		Mockito.when(roleRepository.findById(2)).thenReturn(optionalRole);
		Mockito.when(claimRequestRepository.findByStatus("PENDING")).thenReturn(claimRequests);
		Mockito.when(userRepository.findById(10)).thenReturn(optionalUser);
		Mockito.when(userRepository.findById(101)).thenReturn(optionalUser);
		List<ClaimDto> actualResult = medicalClaimServiceImpl.getClaims(10);
		assertEquals(101, actualResult.get(0).getClaimId().intValue());
	}
	
	@Test
	public void testGetClainDetails() {
		Mockito.when(claimRequestRepository.findById(101)).thenReturn(optionalClaimRequest);
		Mockito.when(userRepository.findByUserId(optionalClaimRequest.get().getUserId())).thenReturn(optionalUser);
		ClaimDetailsDto actualResult = medicalClaimServiceImpl.getClainDetails(101);
		assertEquals("Fortis", actualResult.getHospitalName());
	}
	
	@Test
	public void testApproveMedicalClaim() throws InvalidClaimIdException, InvalidUserException, RemarksEmptyException, RoleNotExistException, UserPolicyNotExistException {
		Mockito.when(userRepository.findById(10)).thenReturn(optionalUser);
		Mockito.when(claimRequestRepository.findById(101)).thenReturn(optionalClaimRequest);
		Mockito.when(userPolicyRepository.findByUserIdAndPolicyId(101, 1010)).thenReturn(optionalUserPolicy);
		Mockito.when(roleRepository.findById(2)).thenReturn(optionalRole);
		Mockito.when(userPolicyRepository.save(optionalUserPolicy.get())).thenReturn(optionalUserPolicy.get());
		Mockito.when(claimRequestRepository.save(optionalClaimRequest.get())).thenReturn(optionalClaimRequest.get());
		ApproveClaimResponseDto actualresult = claimRequestServiceImpl.approveMedicalClaim(101, claimRequestDto);
		assertEquals("Claim has been APPROVED", actualresult.getMessage());
	}
	
	@Test
	public void testSuperPendingApproveMedicalClaim() throws InvalidClaimIdException, InvalidUserException, RemarksEmptyException, RoleNotExistException, UserPolicyNotExistException {
		Mockito.when(userRepository.findById(10)).thenReturn(optionalUser);
		Mockito.when(claimRequestRepository.findById(101)).thenReturn(optionalClaimRequestSuperPending);
		Mockito.when(userPolicyRepository.findByUserIdAndPolicyId(101, 1010)).thenReturn(optionalUserPolicy);
		Mockito.when(roleRepository.findById(2)).thenReturn(optionalRole);
		Mockito.when(userPolicyRepository.save(optionalUserPolicy.get())).thenReturn(optionalUserPolicy.get());
		Mockito.when(claimRequestRepository.save(optionalClaimRequestSuperPending.get())).thenReturn(optionalClaimRequestSuperPending.get());
		ApproveClaimResponseDto actualresult = claimRequestServiceImpl.approveMedicalClaim(101, claimRequestDto);
		assertEquals("Claim has been SUPERPENDING", actualresult.getMessage());
	}
	
	@Test
	public void testSuperAdminApproveMedicalClaim() throws InvalidClaimIdException, InvalidUserException, RemarksEmptyException, RoleNotExistException, UserPolicyNotExistException {
		Mockito.when(userRepository.findById(11)).thenReturn(optionalSuperUser);
		Mockito.when(claimRequestRepository.findById(101)).thenReturn(optionalClaimRequestSuperPending);
		Mockito.when(userPolicyRepository.findByUserIdAndPolicyId(101, 1010)).thenReturn(optionalUserPolicy);
		Mockito.when(roleRepository.findById(3)).thenReturn(optionalSuperRole);
		Mockito.when(userPolicyRepository.save(optionalUserPolicy.get())).thenReturn(optionalUserPolicy.get());
		Mockito.when(claimRequestRepository.save(optionalClaimRequestSuperPending.get())).thenReturn(optionalClaimRequestSuperPending.get());
		ApproveClaimResponseDto actualresult = claimRequestServiceImpl.approveMedicalClaim(101, suerClaimRequestDto);
		assertEquals("Claim has been APPROVED", actualresult.getMessage());
	}
	
	@Test(expected =InvalidUserException.class)
	public void testInvalidUserApproveMedicalClaim() throws InvalidClaimIdException, InvalidUserException, RemarksEmptyException, RoleNotExistException, UserPolicyNotExistException {
		ApproveClaimResponseDto actualresult = claimRequestServiceImpl.approveMedicalClaim(101, claimRequestDto);
		assertEquals("Claim has been APPROVED", actualresult.getMessage());
	}
	
	@Test(expected =InvalidClaimIdException.class)
	public void testInvalidClaimIdApproveMedicalClaim() throws InvalidClaimIdException, InvalidUserException, RemarksEmptyException, RoleNotExistException, UserPolicyNotExistException {
		Mockito.when(userRepository.findById(10)).thenReturn(optionalUser);
		ApproveClaimResponseDto actualresult = claimRequestServiceImpl.approveMedicalClaim(101, claimRequestDto);
		assertEquals("Claim has been APPROVED", actualresult.getMessage());
	}
	
	@Test(expected =RemarksEmptyException.class)
	public void testEmptyRemarksApproveMedicalClaim() throws InvalidClaimIdException, InvalidUserException, RemarksEmptyException, RoleNotExistException, UserPolicyNotExistException {
		Mockito.when(userRepository.findById(10)).thenReturn(optionalUser);
		Mockito.when(claimRequestRepository.findById(101)).thenReturn(optionalClaimRequest);
		ApproveClaimResponseDto actualresult = claimRequestServiceImpl.approveMedicalClaim(101, emptyRemarksClaimRequestDto);
		assertEquals("Claim has been REJECTED", actualresult.getMessage());
	}
	
	@Test
	public void testRejectedApproveMedicalClaim() throws InvalidClaimIdException, InvalidUserException, RemarksEmptyException, RoleNotExistException, UserPolicyNotExistException {
		Mockito.when(userRepository.findById(10)).thenReturn(optionalUser);
		Mockito.when(claimRequestRepository.findById(101)).thenReturn(optionalClaimRequest);
		Mockito.when(claimRequestRepository.save(optionalClaimRequest.get())).thenReturn(optionalClaimRequest.get());
		ApproveClaimResponseDto actualresult = claimRequestServiceImpl.approveMedicalClaim(101, rejectedClaimRequestDto);
		assertEquals("Claim has been REJECTED", actualresult.getMessage());
	}
}
