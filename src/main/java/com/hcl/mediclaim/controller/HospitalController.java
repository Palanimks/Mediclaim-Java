package com.hcl.mediclaim.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hcl.mediclaim.dto.DiseaseDto;
import com.hcl.mediclaim.dto.HospitalDto;
import com.hcl.mediclaim.service.HospitalService;

import lombok.extern.slf4j.Slf4j;

/**
 * Common controller for Hospital
 * 
 * @author Laxman
 * @date 21-OCT-2019
 * 
 */
@Slf4j
@RequestMapping("/")
@RestController
@CrossOrigin(allowedHeaders = { "*", "*/" }, origins = { "*", "*/" })
public class HospitalController {

	@Autowired
	private HospitalService hospitalService;

	
	/**
	 * Method will return the list of hospitals, user is allowed to claim for listed
	 * hospitals
	 * 
	 * @return List<HospitalDto>
	 * 
	 */
	@GetMapping("/hospitals")
	public ResponseEntity<List<HospitalDto>> getHospitals() {
		
		log.info(" :: getHospitals ----");
		return new ResponseEntity<>(hospitalService.getHospitals(), HttpStatus.OK);
	}

	
	/**
	 * Method will return the list of disease which are allowed to claim.
	 * 
	 * @return List<DiseaseDto>
	 */
	@GetMapping("/hospitals/disease")
	public ResponseEntity<List<DiseaseDto>> getDisease() {
		
		log.info(" :: getDisease ----");
		return new ResponseEntity<>(hospitalService.getDisease(), HttpStatus.OK);
	}
}
