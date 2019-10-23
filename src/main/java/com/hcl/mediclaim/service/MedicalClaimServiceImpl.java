package com.hcl.mediclaim.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hcl.mediclaim.dto.ClaimDetailsDto;
import com.hcl.mediclaim.dto.ClaimDto;
import com.hcl.mediclaim.entity.ClaimRequest;
import com.hcl.mediclaim.entity.Role;
import com.hcl.mediclaim.entity.User;
import com.hcl.mediclaim.repository.ClaimRequestRepository;
import com.hcl.mediclaim.repository.RoleRepository;
import com.hcl.mediclaim.repository.UserRepository;
import com.hcl.mediclaim.utility.RoleType;
import com.hcl.mediclaim.utility.StatusType;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MedicalClaimServiceImpl implements MedicalClaimService {

	@Autowired
	private ClaimRequestRepository claimRequestRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	/**
	 * Method will return the list of claim based on login, if APPROVER has login
	 * then record will show for approver level when SUPPER APPROVER will login
	 * record will show for SUPPER APPROVER
	 * 
	 * @param userId
	 * @return List<ClaimDto>
	 */
	@Override
	public List<ClaimDto> getClaims(Integer userId) {

		log.info(" :: getClaims --- START -- userId : {}",userId);
		List<ClaimRequest> claimRequests =  new ArrayList<>();
		List<ClaimDto> claimsDto = new ArrayList<>();
		
		Optional<User> optionalUser = userRepository.findById(userId);
		
		log.info(" :: getClaims ----- {}",optionalUser);
		if(optionalUser.isPresent()) {
			Optional<Role> optionalRole = roleRepository.findById(optionalUser.get().getRoleId());
			String status = null;
			if(optionalRole.isPresent()) {
				if(optionalRole.get().getRoleName().equals(RoleType.ADMIN.name())) {
					status = StatusType.PENDING.name();
				} else if(optionalRole.get().getRoleName().equals(RoleType.SUPER_ADMIN.name())) {
					status = StatusType.SUPERPENDING.name();
				}
				claimRequests = claimRequestRepository.findByStatus(status);
			}
		}

		claimRequests.stream().forEach(claimRequest -> {
			ClaimDto claimDto = new ClaimDto();
			BeanUtils.copyProperties(claimRequest, claimDto);
			Optional<User> optUser = userRepository.findById(claimRequest.getUserId());
			claimDto.setUserName(optUser.get().getUserName());
			claimsDto.add(claimDto);
		});
		log.info(" :: getClaims --- END -- ");
		return claimsDto;
	}

	/**
	 * Method show the details of particular claim based on claim id.
	 * 
	 * @param claimId
	 * @return ClaimDetailsDto
	 */
	@Override
	public ClaimDetailsDto getClainDetails(Integer claimId) {
		
		log.info(" :: getClainDetails --- START ---");
		ClaimDetailsDto claimDetailsDto = new ClaimDetailsDto();
		Optional<ClaimRequest> optionalClaimRequest = claimRequestRepository.findById(claimId);
		if(optionalClaimRequest.isPresent()) {
			BeanUtils.copyProperties(optionalClaimRequest.get(), claimDetailsDto);
			Optional<User> optionalUser = userRepository.findByUserId(optionalClaimRequest.get().getUserId());
			if(optionalUser.isPresent()) {
				claimDetailsDto.setUserName(optionalUser.get().getUserName());
			}
		}
		log.info(" :: getClainDetails --- END ---");
		return claimDetailsDto;
	}

}
