package com.hcl.mediclaim.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClaimDto {

	private Integer claimId;
	private String userName;
	private Integer policyId;
	private LocalDate claimDate;
	private Double claimAmount;
	private String status;
}
