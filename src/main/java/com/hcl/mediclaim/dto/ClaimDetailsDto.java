package com.hcl.mediclaim.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClaimDetailsDto {

	private Integer claimId;
	private String userName;
	private Integer policyId;
	private String hospitalName;
	private String diagnosis;
	private LocalDate admissionDate;
	private LocalDate dischargeDate;
	private LocalDate claimDate;
	private Double claimAmount;
	private String status;
	private Integer approverId;
}
