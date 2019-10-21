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

@Slf4j
@RequestMapping("/")
@RestController
@CrossOrigin(allowedHeaders = { "*", "*/" }, origins = { "*", "*/" })
public class HospitalController {

	@Autowired
	private HospitalService hospitalService;
	
	@GetMapping("/hospitals")
	public ResponseEntity<List<HospitalDto>> getHospitals(){
		return new ResponseEntity<>(hospitalService.getHospitals(), HttpStatus.OK);
	}
	
	@GetMapping("/hospitals/disease")
	public ResponseEntity<List<DiseaseDto>> getDisease(){
		return new ResponseEntity<>(hospitalService.getDisease(), HttpStatus.OK);
	}
}
