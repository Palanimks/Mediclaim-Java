package com.hcl.mediclaim.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class LoginResponseDto {

	private String status;
	private String message;
	private String userName;
	private Integer userId;
}
