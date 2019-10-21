package com.hcl.mediclaim.service;

import org.springframework.web.bind.annotation.RequestBody;

import com.hcl.mediclaim.dto.LoginRequestDto;
import com.hcl.mediclaim.dto.LoginResponseDto;

public interface LoginService {
	LoginResponseDto login(@RequestBody LoginRequestDto loginRequestDto);
}
