package com.hcl.mediclaim.service;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.hcl.mediclaim.dto.DiseaseDto;
import com.hcl.mediclaim.dto.HospitalDto;
import com.hcl.mediclaim.entity.Disease;
import com.hcl.mediclaim.entity.Hospital;
import com.hcl.mediclaim.repository.DiseaseRepository;
import com.hcl.mediclaim.repository.HospitalRepository;

/**
 * test case for Hospital service. i.e to get hospital list and Disease list
 * 
 * @author Laxman
 * @date 22-OCT-2019
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class HospoitalServiceTest {

	@InjectMocks
	private HospitalServiceImpl hospitalServiceImpl;

	@Mock
	private HospitalRepository hospitalRepository;

	@Mock
	private DiseaseRepository diseaseRepository;

	private List<Hospital> hospitals = null;
	private List<Disease> diseases = null;

	@Before
	public void setUp() {
		hospitals = new ArrayList<>();
		Hospital hospital = new Hospital();
		hospital.setHospitalId(101);
		hospital.setHospitalname("Fortis");
		hospitals.add(hospital);

		diseases = new ArrayList<>();
		Disease disease = new Disease();
		disease.setDiseaseId(1010);
		disease.setDiseaseName("Fever");
		diseases.add(disease);
	}

	@Test
	public void getHospitalsTest() {
		Mockito.when(hospitalRepository.findAll()).thenReturn(hospitals);
		List<HospitalDto> actualResult = hospitalServiceImpl.getHospitals();
		assertEquals("Fortis", actualResult.get(0).getHospitalname());
	}

	@Test
	public void getDiseaseTest() {
		Mockito.when(diseaseRepository.findAll()).thenReturn(diseases);
		List<DiseaseDto> actualResult = hospitalServiceImpl.getDisease();
		assertEquals("Fever", actualResult.get(0).getDiseaseName());
	}
}
