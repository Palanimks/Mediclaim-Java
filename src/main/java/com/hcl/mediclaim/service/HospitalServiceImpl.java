package com.hcl.mediclaim.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hcl.mediclaim.dto.DiseaseDto;
import com.hcl.mediclaim.dto.HospitalDto;
import com.hcl.mediclaim.entity.Disease;
import com.hcl.mediclaim.entity.Hospital;
import com.hcl.mediclaim.repository.DiseaseRepository;
import com.hcl.mediclaim.repository.HospitalRepository;

@Service
public class HospitalServiceImpl implements HospitalService {

	@Autowired
	private HospitalRepository hospitalRepository;
	
	@Autowired
	private DiseaseRepository diseaseRepository;
	
	@Override
	public List<HospitalDto> getHospitals() {
		List<HospitalDto> hospitalsDto = new ArrayList<>();
		List<Hospital> hospitals = hospitalRepository.findAll();

		hospitals.stream().forEach(hospital -> {
			HospitalDto hospitalDto = new HospitalDto(); 
			BeanUtils.copyProperties(hospital, hospitalDto);
			hospitalsDto.add(hospitalDto);
		});
		return hospitalsDto;
	}

	@Override
	public List<DiseaseDto> getDisease() {
		List<DiseaseDto> diseasesDto = new ArrayList<>();
		List<Disease> diseases = diseaseRepository.findAll();
		diseases.stream().forEach(disease -> {
			DiseaseDto diseaseDto = new DiseaseDto();
			BeanUtils.copyProperties(disease, diseaseDto);
			diseasesDto.add(diseaseDto);
		});
		return diseasesDto;
	}

}
