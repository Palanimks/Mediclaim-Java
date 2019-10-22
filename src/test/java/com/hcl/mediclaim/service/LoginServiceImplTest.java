package com.hcl.mediclaim.service;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import com.hcl.mediclaim.dto.LoginRequestDto;
import com.hcl.mediclaim.dto.LoginResponseDto;
import com.hcl.mediclaim.entity.User;
import com.hcl.mediclaim.repository.UserRepository;

import junit.framework.Assert;




@RunWith(SpringRunner.class)
public class LoginServiceImplTest {

	@InjectMocks
	LoginServiceImpl LoginServiceImpl;

	@Mock
	UserRepository userRepository;

	User user=null;
	LoginRequestDto loginRequestDto = null;
	Optional<User> optUser = null;
	@Before
	public void init() {
		loginRequestDto=new LoginRequestDto();
		user = new User();
		user.setEmail("abc@gmail.com");
		user.setAadhaarNumber(12345l);
		user.setRoleId(1);
		user.setUserName("vijay");
		user.setMobileNumber(742232l);
		user.setUserId(12);
		user.setPassword("9999");
		loginRequestDto.setEmail("abc@gmail.com");
		loginRequestDto.setPassword("9999");
		
		optUser = Optional.of(user);
	}
	
	@Test
	public void loginTest() {

		Mockito.when(userRepository.findByEmailAndPassword(loginRequestDto.getEmail(), loginRequestDto.getPassword()))
				.thenReturn(optUser);
		LoginResponseDto actualValue = LoginServiceImpl.login(loginRequestDto);
		Assert.assertEquals("Success", actualValue.getStatus());

	}

}