package com.lovepink.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

import com.lovepink.model.request.createUserRequest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Table
@NoArgsConstructor
@AllArgsConstructor
@Component
@Entity(name = "users")
public class Users implements Serializable {
	@Id
	private String username;
	private String password;
	private String name;
	private String address;
	private String phone;
	private String email;
	private String image;
	private String role;
//	@ElementCollection
//	@Column(name = "")
	public static Users toUser(createUserRequest req) {
		Users user = new Users();
		user.setUsername(req.getUsername());
		user.setPassword(req.getPassword());
		user.setName(req.getName());
		user.setAddress(req.getAddress());
		user.setPhone(req.getPhone());
		user.setEmail(req.getEmail());
		user.setImage(req.getImage());
		user.setRole(req.getRole());
		return  user;
	}
}
