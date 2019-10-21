package com.hcl.mediclaim.entity;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table
public class ClaimRequest {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer claimId;
	private Integer userId;
	private Integer policyId;
	private String hospitalName;
	private String diagnosis;
	private LocalDate admissionDate;
	private LocalDate dischargeDate;
	private Double claimAmount;
	private String status;
	private LocalDate claimDate;
	private Integer approverId;
	private LocalDate approvalDate;
	private String remarks;
}
