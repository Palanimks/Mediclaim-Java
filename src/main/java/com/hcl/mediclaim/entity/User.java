package com.hcl.mediclaim.entity;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class User {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer userId;
	private Integer roleId;
	private String userName;
	private String email;
	private String password;
	private Long mobileNumber;
	private LocalDate dateOfBirth;
	private Long aadhaarNumber;
}
