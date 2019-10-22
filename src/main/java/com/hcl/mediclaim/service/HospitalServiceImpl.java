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

import lombok.extern.slf4j.Slf4j;

/**
 * Hospital service Class
 * 
 * @author Laxman
 * @date 21-OCT-2019
 *
 */
@Slf4j
@Service
public class HospitalServiceImpl implements HospitalService {

	@Autowired
	private HospitalRepository hospitalRepository;
	
	@Autowired
	private DiseaseRepository diseaseRepository;
	
	/**
	 * Method will return the list of hospitals, user is allowed to claim for listed
	 * hospitals
	 * 
	 * @return List<HospitalDto>
	 * 
	 */
	@Override
	public List<HospitalDto> getHospitals() {
		
		log.info(" :: getHospitals -----STARTS ---- ");
		List<HospitalDto> hospitalsDto = new ArrayList<>();
		List<Hospital> hospitals = hospitalRepository.findAll();

		log.info(" :: getHospitals -----hospitals ---- {} ",hospitals.size());
		hospitals.stream().forEach(hospital -> {
			HospitalDto hospitalDto = new HospitalDto(); 
			BeanUtils.copyProperties(hospital, hospitalDto);
			hospitalsDto.add(hospitalDto);
		});
		log.info(" :: getHospitals -----END ---- ");
		return hospitalsDto;
	}

	/**
	 * Method will return the list of disease which are allowed to claim.
	 * 
	 * @return List<DiseaseDto>
	 */
	@Override
	public List<DiseaseDto> getDisease() {
		
		log.info(" :: getDisease -----STARTS ---- ");
		List<DiseaseDto> diseasesDto = new ArrayList<>();
		List<Disease> diseases = diseaseRepository.findAll();
		
		log.info(" :: getDisease -----diseases -- {}",diseases.size());
		diseases.stream().forEach(disease -> {
			DiseaseDto diseaseDto = new DiseaseDto();
			BeanUtils.copyProperties(disease, diseaseDto);
			diseasesDto.add(diseaseDto);
		});
		log.info(" :: getDisease -----END ---- ");
		return diseasesDto;
	}

}
