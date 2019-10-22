package com.hcl.mediclaim.service;

import java.util.List;

import com.hcl.mediclaim.dto.DiseaseDto;
import com.hcl.mediclaim.dto.HospitalDto;

/**
 * Hospital service interface
 * 
 * @author Laxman
 * @date 21-OCT-2019
 *
 */
public interface HospitalService {

	public List<HospitalDto> getHospitals();

	public List<DiseaseDto> getDisease();

}
