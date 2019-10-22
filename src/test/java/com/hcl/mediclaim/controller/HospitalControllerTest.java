package com.hcl.mediclaim.controller;

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
import org.springframework.http.ResponseEntity;

import com.hcl.mediclaim.dto.DiseaseDto;
import com.hcl.mediclaim.dto.HospitalDto;
import com.hcl.mediclaim.service.HospitalServiceImpl;

/**
 * test case for Hospital Controller. i.e to get hospital list and Disease list
 * 
 * @author Laxman
 * @date 22-OCT-2019
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class HospitalControllerTest {

	@Mock
	private HospitalServiceImpl hospitalServiceImpl;
	
	@InjectMocks
	private HospitalController hospitalController;
	
	private List<HospitalDto> hospitalsDto = null;
	private List<DiseaseDto> diseasesDto = null;
	
	@Before
	public void setUp() {
		hospitalsDto = new ArrayList<>();
		HospitalDto hospitalDto = new HospitalDto();
		hospitalDto.setHospitalId(101);
		hospitalDto.setHospitalname("Fortis");
		hospitalsDto.add(hospitalDto);
		
		diseasesDto = new ArrayList<>();
		DiseaseDto diseaseDto =new DiseaseDto();
		diseaseDto.setDiseaseId(1010);
		diseaseDto.setDiseaseName("Fever");
		diseasesDto.add(diseaseDto);
	}
	
	@Test
	public void getHospitalsTest() {
		Mockito.when(hospitalServiceImpl.getHospitals()).thenReturn(hospitalsDto);
		ResponseEntity<List<HospitalDto>> actualResponse = hospitalController.getHospitals();
		assertEquals(200, actualResponse.getStatusCode().value());
	}
	
	@Test
	public void  getDiseaseTest() {
		Mockito.when(hospitalServiceImpl.getDisease()).thenReturn(diseasesDto);
		ResponseEntity<List<DiseaseDto>> actualResult = hospitalController.getDisease();
		assertEquals(200, actualResult.getStatusCode().value());
	}
}
