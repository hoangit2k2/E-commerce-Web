package com.lovepink.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lovepink.model.request.createUserRequest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.lovepink.entity.Content;
import com.lovepink.entity.Likes;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
@Data
@Table
@NoArgsConstructor
@AllArgsConstructor
@Component
@Entity(name = "users")
public class Users implements Serializable {
	@Id
	@Column(name="username")
	private String username;
	private String password;
	private String name;
	private String address;
	private String phone;
	private String email;
	private String image;
	private String role;
//	@JsonIgnore
//	@OneToMany(mappedBy = "usernameid")
//	<Likes> likes;
//	@JsonIgnore
//	@OneToMany(mappedBy = "usernameid")
//	List<Content> content;

//	@JsonIgnore
//	@OneToMany(mappedBy = "role")
//	List<Authority> authorities;
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
