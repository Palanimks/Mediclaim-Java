package com.hcl.mediclaim.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hcl.mediclaim.dto.LoginRequestDto;
import com.hcl.mediclaim.dto.LoginResponseDto;
import com.hcl.mediclaim.entity.User;
import com.hcl.mediclaim.repository.UserRepository;

@Service
public class LoginServiceImpl implements LoginService{
	@Autowired
	UserRepository userRepository;


	public LoginResponseDto login(LoginRequestDto loginRequestDto) {
		LoginResponseDto loginResponseDto = null;

		Optional<User> user = userRepository.findByEmailAndPassword(loginRequestDto.getEmail(),
				loginRequestDto.getPassword());

		if (user.isPresent()) {
			loginResponseDto = new LoginResponseDto();
			loginResponseDto.setMessage("Login successfully.");
			loginResponseDto.setStatus("Success");
			loginResponseDto.setUserName(user.get().getUserName());
			loginResponseDto.setUserId(user.get().getUserId());
		
		}

		return loginResponseDto;
	}

}
