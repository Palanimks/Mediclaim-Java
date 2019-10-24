package com.hcl.mediclaim.service;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hcl.mediclaim.dto.ApproveClaimRequestDto;
import com.hcl.mediclaim.dto.ApproveClaimResponseDto;
import com.hcl.mediclaim.dto.MedicalClaimRequestDto;
import com.hcl.mediclaim.dto.MedicalClaimResponseDto;
import com.hcl.mediclaim.entity.ClaimRequest;
import com.hcl.mediclaim.entity.Policy;
import com.hcl.mediclaim.entity.Role;
import com.hcl.mediclaim.entity.User;
import com.hcl.mediclaim.entity.UserPolicy;
import com.hcl.mediclaim.exception.InvalidClaimIdException;
import com.hcl.mediclaim.exception.InvalidPolicyIdException;
import com.hcl.mediclaim.exception.InvalidUserException;
import com.hcl.mediclaim.exception.RemarksEmptyException;
import com.hcl.mediclaim.exception.RoleNotExistException;
import com.hcl.mediclaim.exception.UserPolicyNotExistException;
import com.hcl.mediclaim.repository.ClaimRequestRepository;
import com.hcl.mediclaim.repository.PolicyRepository;
import com.hcl.mediclaim.repository.RoleRepository;
import com.hcl.mediclaim.repository.UserPolicyRepository;
import com.hcl.mediclaim.repository.UserRepository;
import com.hcl.mediclaim.utility.MediClaimUtility;
import com.hcl.mediclaim.utility.RoleType;
import com.hcl.mediclaim.utility.StatusType;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author Sushil This service class is use to apply for medical claim
 *
 */
@Service
@Slf4j
public class ClaimRequestServiceImpl implements ClaimRequestService {

	@Autowired
	UserPolicyRepository userPolicyRepository;

	@Autowired
	PolicyRepository policyRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	ClaimRequestRepository claimRequestRepository;

	/**
	 * This method is use to apply for medical claim
	 * 
	 * @param MedicalClaimRequestDto, not null
	 * @param MedicalClaimResponseDto ,not null
	 * @throws InvalidUserException     if user does not exist
	 * @throws InvalidPolicyIdException if policy id does not exist
	 */
	@Override
	public MedicalClaimResponseDto applyMedicalClaim(MedicalClaimRequestDto medicalClaimRequestDto)
			throws InvalidUserException, InvalidPolicyIdException {
		log.info("Inside applyMedicalClaim of ClaimRequestServiceImpl");
		MedicalClaimResponseDto responseDto;
		/* get user object */

		Optional<User> user = userRepository.findById(medicalClaimRequestDto.getUserId());
		if (user.isPresent()) {
			if (user.get().getUserId() == medicalClaimRequestDto.getUserId()
					&& user.get().getAadhaarNumber() == medicalClaimRequestDto.getAadhaarNumber()) {
				Optional<Policy> policy = policyRepository.findById(medicalClaimRequestDto.getPolicyId());
				if (policy.isPresent()) {
					ClaimRequest claimRequest = new ClaimRequest();

					BeanUtils.copyProperties(medicalClaimRequestDto, claimRequest);
					claimRequest.setClaimDate(LocalDate.now());
					claimRequest.setStatus(MediClaimUtility.CLAIM_PENDING_STATUS);
					/* save claim object */
					ClaimRequest claimRequestResponse = claimRequestRepository.save(claimRequest);
					/* prepare response */
					responseDto = new MedicalClaimResponseDto();
					responseDto.setClaimId(claimRequestResponse.getClaimId());
					responseDto.setStatus(MediClaimUtility.SUCCESS_RESPONSE);
					responseDto.setMessage(MediClaimUtility.CLAIM_SUCCESS_REMARK);

				} else {
					throw new InvalidPolicyIdException(MediClaimUtility.INVALID_POLICYID_EXCEPTION);
				}
			} else {
				throw new InvalidUserException(MediClaimUtility.INVALID_USER_EXCEPTION_AADHAR_OR_USERID);
			}
		} else {
			throw new InvalidUserException(MediClaimUtility.INVALID_USER_EXCEPTION);
		}
		return responseDto;
	}

	/**
	 * This method is use to approve medical claim
	 * 
	 * @param claimId,not null
	 * @param claimRequestDto ,not null
	 * @throws InvalidClaimIdException, if claim id does not exist
	 * @throws InvalidUserException, if user id does not exist
	 * @throws RemarksEmptyException ,if remark is empty
	 * @throws RoleNotExistException ,if role does not exist
	 * @throws UserPolicyNotExistException ,if user policy does not exist
	 */
	@Override
	public ApproveClaimResponseDto approveMedicalClaim(int claimId, ApproveClaimRequestDto claimRequestDto)
			throws InvalidClaimIdException, InvalidUserException, RemarksEmptyException, RoleNotExistException, UserPolicyNotExistException {
		log.info("Inside approveMedicalClaim of ClaimRequestServiceImpl");

		ApproveClaimResponseDto approveClaimResponseDto = new ApproveClaimResponseDto();
		Optional<User> user = userRepository.findById(claimRequestDto.getUserId());
		if (!user.isPresent()) {
			throw new InvalidUserException(MediClaimUtility.INVALID_USER_EXCEPTION);
		}
		Optional<ClaimRequest> optionalClaimRequest = claimRequestRepository.findById(claimId);
		if (!optionalClaimRequest.isPresent()) {
			throw new InvalidClaimIdException(MediClaimUtility.INVALID_CLAIM_ID_EXCEPTION);
		}
		if (claimRequestDto.getStatus().equals(StatusType.REJECTED.name())
				&& (claimRequestDto.getRemarks() == null || claimRequestDto.getRemarks().isEmpty())) {
			throw new RemarksEmptyException(MediClaimUtility.ENTER_REMARKS);
		}
		
		ClaimRequest claimRequest = optionalClaimRequest.get();
		claimRequest.setApproverId(user.get().getUserId());
		claimRequest.setApprovalDate(LocalDate.now());
		claimRequest.setRemarks(claimRequestDto.getRemarks());
		
		if (!claimRequestDto.getStatus().equals(StatusType.REJECTED.name())) {
			Optional<UserPolicy> optionalUserPolicy = userPolicyRepository
					.findByUserIdAndPolicyId(claimRequest.getUserId(), claimRequest.getPolicyId());
			Optional<Role> optRole = roleRepository.findById(user.get().getRoleId());

			if (!optRole.isPresent()) {
				throw new RoleNotExistException(MediClaimUtility.ROLE_NOT_EXIST_MESSAGE);
			}
			if (!optionalUserPolicy.isPresent()) {
				throw new UserPolicyNotExistException(MediClaimUtility.USER_POLICY_NOT_EXIST_MESSAGE);
			}
			
			UserPolicy userPolicy = optionalUserPolicy.get();
			Role role = optRole.get();
			
			if (role.getRoleName().equals(RoleType.SUPER_ADMIN.name())
					&& userPolicy.getBalanceAmount() < claimRequest.getClaimAmount()) {
				claimRequest.setApprovedAmount(optionalUserPolicy.get().getBalanceAmount());
				optionalUserPolicy.get().setBalanceAmount(0);
				claimRequest.setStatus(StatusType.APPROVED.name());
				approveClaimResponseDto.setMessage(MediClaimUtility.CLAIM_RESPONSE_MESSAGE + StatusType.APPROVED.name());
				
			} else if (role.getRoleName().equals(RoleType.ADMIN.name())
					&& (userPolicy.getBalanceAmount() < claimRequest.getClaimAmount())) {
				claimRequest.setStatus(StatusType.SUPERPENDING.name());
				approveClaimResponseDto.setMessage(MediClaimUtility.CLAIM_RESPONSE_MESSAGE + StatusType.SUPERPENDING.name());
				
			} else if (role.getRoleName().equals(RoleType.ADMIN.name())
					&& (userPolicy.getBalanceAmount() >= claimRequest.getClaimAmount())) {
				userPolicy.setBalanceAmount(userPolicy.getBalanceAmount() - claimRequest.getClaimAmount());
				claimRequest.setApprovedAmount(claimRequest.getClaimAmount());
				claimRequest.setStatus(StatusType.APPROVED.name());
				approveClaimResponseDto.setMessage(MediClaimUtility.CLAIM_RESPONSE_MESSAGE + StatusType.APPROVED.name());
			}
			userPolicyRepository.save(userPolicy);
		} else {
			claimRequest.setStatus(StatusType.REJECTED.name());
			approveClaimResponseDto.setMessage(MediClaimUtility.CLAIM_RESPONSE_MESSAGE + StatusType.REJECTED.name());
		}
		claimRequestRepository.save(claimRequest);

		approveClaimResponseDto.setStatus(MediClaimUtility.SUCCESS_RESPONSE);
		return approveClaimResponseDto;
	}

}
