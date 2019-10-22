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
import com.hcl.mediclaim.entity.User;
import com.hcl.mediclaim.exception.InvalidClaimIdException;
import com.hcl.mediclaim.exception.InvalidPolicyIdException;
import com.hcl.mediclaim.exception.InvalidUserException;
import com.hcl.mediclaim.repository.ClaimRequestRepository;
import com.hcl.mediclaim.repository.PolicyRepository;
import com.hcl.mediclaim.repository.UserPolicyRepository;
import com.hcl.mediclaim.repository.UserRepository;
import com.hcl.mediclaim.utility.MediClaimUtility;

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
			if (user.get().getUserId() == MediClaimUtility.USER_ROLE_ID
					&& user.get().getAadhaarNumber() == medicalClaimRequestDto.getAadhaarNumber()) {
				Optional<Policy> policy = policyRepository.findById(medicalClaimRequestDto.getPolicyId());
				if (policy.isPresent()) {
					ClaimRequest claimRequest = new ClaimRequest();

					BeanUtils.copyProperties(medicalClaimRequestDto, claimRequest);
					claimRequest.setClaimDate(LocalDate.now());
					claimRequest.setStatus(MediClaimUtility.CLAIM_PENDING_STATUS);
					/*save claim object*/
					ClaimRequest claimRequestResponse  = claimRequestRepository.save(claimRequest);
					/*prepare response*/
					responseDto = new MedicalClaimResponseDto();
					responseDto.setClaimId(claimRequestResponse.getClaimId());
					responseDto.setStatus(MediClaimUtility.SUCCESS_RESPONSE);
					responseDto.setMessage(MediClaimUtility.CLAIM_SUCCESS_REMARK);
					
				}
				 else {
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
     * @param claimId,not null
     * @param 
     */
	@Override
	public ApproveClaimResponseDto approveMedicalClaim(int claimId, ApproveClaimRequestDto claimRequestDto) throws InvalidClaimIdException, InvalidUserException {
		log.info("Inside approveMedicalClaim of ClaimRequestServiceImpl");
		
		Optional<User> user = userRepository.findById(claimRequestDto.getUserId());
		if (user.isPresent()) {
			
			if(true)
			{
				/*get claim request object*/
				Optional<ClaimRequest>  claimRequest_o = claimRequestRepository.findById(claimId);
				
				if(claimRequest_o.isPresent())
				{
					ClaimRequest claimRequest  = claimRequest_o.get();
					Optional<Policy> policy = policyRepository.findById(claimRequest.getPolicyId());
					if(policy.get().getTotalSumInsured()>=claimRequest.getClaimAmount())
					{
						claimRequest.setApprovalDate(LocalDate.now());
						claimRequest.setApproverId(claimRequestDto.getUserId());
					}
					else
					{
						//send to super user approval
					}
				}
				else
				{
					throw new InvalidClaimIdException(MediClaimUtility.INVALID_CLAIM_ID_EXCEPTION);
				}
			}
			else
			{
				// 
			}
			
		}
		else {
			throw new InvalidUserException(MediClaimUtility.INVALID_USER_EXCEPTION);
		}

		return null;
	}

}
