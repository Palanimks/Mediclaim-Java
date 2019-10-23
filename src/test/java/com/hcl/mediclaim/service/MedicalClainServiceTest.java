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

import com.hcl.mediclaim.dto.ClaimDetailsDto;
import com.hcl.mediclaim.dto.ClaimDto;
import com.hcl.mediclaim.entity.ClaimRequest;
import com.hcl.mediclaim.entity.Role;
import com.hcl.mediclaim.entity.User;
import com.hcl.mediclaim.repository.ClaimRequestRepository;
import com.hcl.mediclaim.repository.RoleRepository;
import com.hcl.mediclaim.repository.UserRepository;

@RunWith(MockitoJUnitRunner.class)
public class MedicalClainServiceTest {

	@Mock
	private ClaimRequestRepository claimRequestRepository;
	
	@Mock
	private UserRepository userRepository;
	
	@Mock
	private RoleRepository roleRepository;
	
	@InjectMocks
	private MedicalClaimServiceImpl medicalClaimServiceImpl;
	
	Optional<User> optionalUser = null;
	Optional<Role> optionalRole = null;
	List<ClaimRequest> claimRequests = null;
	Optional<ClaimRequest> optionalClaimRequest = null;
	
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
		claimRequest.setUserId(10);
		claimRequests = new ArrayList<>();
		claimRequests.add(claimRequest);
		optionalClaimRequest = Optional.of(claimRequest); 
	}
	
	@Test
	public void getClaimsTest() {
		Mockito.when(userRepository.findById(10)).thenReturn(optionalUser);
		Mockito.when(roleRepository.findById(optionalUser.get().getRoleId())).thenReturn(optionalRole);
		Mockito.when(claimRequestRepository.findByStatus("PENDING")).thenReturn(claimRequests);
		Mockito.when(userRepository.findById(10)).thenReturn(optionalUser);
		List<ClaimDto> actualResult = medicalClaimServiceImpl.getClaims(10);
		assertEquals(101, actualResult.get(0).getClaimId().intValue());
	}
	
	@Test
	public void getClainDetailsTest() {
		Mockito.when(claimRequestRepository.findById(101)).thenReturn(optionalClaimRequest);
		Mockito.when(userRepository.findByUserId(optionalClaimRequest.get().getUserId())).thenReturn(optionalUser);
		ClaimDetailsDto actualResult = medicalClaimServiceImpl.getClainDetails(101);
		assertEquals("Fortis", actualResult.getHospitalName());
	}
	
}
