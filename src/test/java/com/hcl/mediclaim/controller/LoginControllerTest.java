package com.hcl.mediclaim.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

import com.hcl.mediclaim.dto.LoginRequestDto;
import com.hcl.mediclaim.dto.LoginResponseDto;
import com.hcl.mediclaim.service.LoginServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class LoginControllerTest {

	@InjectMocks
	LoginController loginControllerMock;

	@Mock
	LoginServiceImpl loginServiceImpl;

	@Test
	public void testLogin() {

		LoginResponseDto loginResponseDto = new LoginResponseDto();
		loginResponseDto.setUserName("vijay");
		loginResponseDto.setMessage("success");
		loginResponseDto.setUserId(1);
		loginResponseDto.setStatus("success");

		when(loginServiceImpl.login(Mockito.any())).thenReturn(loginResponseDto);

		LoginRequestDto loginRequestDto = new LoginRequestDto();
		loginRequestDto.setPassword("ok");
		loginRequestDto.setEmail("abc@gmail.com");
		ResponseEntity<LoginResponseDto> actual = loginControllerMock.login(loginRequestDto);

		assertEquals("success", actual.getBody().getStatus());
	}
}
