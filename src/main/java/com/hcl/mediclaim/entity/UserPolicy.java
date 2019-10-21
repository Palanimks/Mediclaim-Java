package com.hcl.mediclaim.entity;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class UserPolicy {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int userPolicyId;
	private int userId;
	private int policyId;
	private double balanceAmount;
	private LocalDate issuedDate;
	private LocalDate expiryDate;
	

}
