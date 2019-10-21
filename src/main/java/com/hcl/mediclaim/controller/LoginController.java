package com.hcl.mediclaim.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.hcl.mediclaim.dto.LoginRequestDto;
import com.hcl.mediclaim.dto.LoginResponseDto;
import com.hcl.mediclaim.service.LoginService;

import lombok.extern.slf4j.Slf4j;

/*
 * @author vijay
 * 
 * */
@Slf4j
@RequestMapping("/")
@RestController
@CrossOrigin(allowedHeaders = { "*", "*/" }, origins = { "*", "*/" })
public class LoginController {

	@Autowired
	LoginService loginService;

	/*
	 * for login
	 * @RequestBody LoginRequestDto
	 */
	@PostMapping("/login")
	public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDto) {
		log.info("inside Mediclaim  LOGIN");
		LoginResponseDto loginResponseDto = loginService.login(loginRequestDto);
		return new ResponseEntity<>(loginResponseDto, HttpStatus.CREATED);
	}

}