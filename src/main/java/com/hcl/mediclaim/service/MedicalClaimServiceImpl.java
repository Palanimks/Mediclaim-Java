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

@Service
public class MedicalClaimServiceImpl implements MedicalClaimService {

	@Autowired
	private ClaimRequestRepository claimRequestRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Override
	public List<ClaimDto> getClaims(Integer userId) {

		List<ClaimRequest> claimRequests = null;
		List<ClaimDto> claimsDto = new ArrayList<>();
		
		Optional<User> optionalUser = userRepository.findById(userId);
		if(optionalUser.isPresent()) {
			Optional<Role> optionalRole = roleRepository.findById(optionalUser.get().getRoleId());
			if(optionalRole.isPresent()) {
				if(optionalRole.get().getRoleName().equals(RoleType.ADMIN.name())) {
					claimRequests = claimRequestRepository.findByStatus(StatusType.PENDING.name());
				} else if(optionalRole.get().getRoleName().equals(RoleType.SUPER_ADMIN.name())) {
					claimRequests = claimRequestRepository.findByStatus(StatusType.SUPERPENDING.name());
				}
			}
		}

		claimRequests.stream().forEach(claimRequest -> {
			ClaimDto claimDto = new ClaimDto();
			BeanUtils.copyProperties(claimRequest, claimDto);
			claimsDto.add(claimDto);
		});
		
		return claimsDto;
	}

	@Override
	public ClaimDetailsDto getClainDetails(Integer claimId) {
		ClaimDetailsDto claimDetailsDto = new ClaimDetailsDto();
		Optional<ClaimRequest> optionalClaimRequest = claimRequestRepository.findById(claimId);
		if(optionalClaimRequest.isPresent()) {
			BeanUtils.copyProperties(optionalClaimRequest.get(), claimDetailsDto);
			Optional<User> optionalUser = userRepository.findByUserId(optionalClaimRequest.get().getUserId());
			if(optionalUser.isPresent()) {
				claimDetailsDto.setUserName(optionalUser.get().getUserName());
			}
		}
		return claimDetailsDto;
	}

}
