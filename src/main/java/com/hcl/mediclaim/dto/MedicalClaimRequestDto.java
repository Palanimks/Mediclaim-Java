package com.hcl.mediclaim.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MedicalClaimRequestDto {
	
	private int userId;
	private int policyId;
	private String diagnosis;
	private LocalDate admissionDate;
	private LocalDate dischargeDate;
	private double claimAmount;
	private String hospitalName;
	private LocalDate dateOfBirth;
	private long aadhaarNumber;

}
