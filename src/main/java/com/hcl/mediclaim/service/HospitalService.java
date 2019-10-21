package com.hcl.mediclaim.service;

import java.util.List;

import com.hcl.mediclaim.dto.DiseaseDto;
import com.hcl.mediclaim.dto.HospitalDto;

public interface HospitalService {

	public List<HospitalDto> getHospitals();

	public List<DiseaseDto> getDisease();

}
