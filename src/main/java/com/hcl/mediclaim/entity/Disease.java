package com.hcl.mediclaim.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Disease {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer diseaseId;
	private String diseaseName;
}
