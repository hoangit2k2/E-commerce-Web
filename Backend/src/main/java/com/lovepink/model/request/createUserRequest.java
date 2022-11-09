package com.lovepink.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class createUserRequest {
	private String username;
	private String password;
	private String name;
	private String address;
	private String phone;
	private String email;
	private String image;
	private String role;
}
