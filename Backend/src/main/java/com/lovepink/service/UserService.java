package com.lovepink.service;

import java.util.List;

import com.lovepink.entity.Users;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import com.lovepink.model.request.createUserRequest;

public interface UserService {
	Users findById(String username);
	List<Users> findAll();
	String deleteUser(String username);
	Users createuser(createUserRequest req);

}
