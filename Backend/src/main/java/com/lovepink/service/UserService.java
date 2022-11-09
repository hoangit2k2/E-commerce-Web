package com.lovepink.service;

import java.util.List;
import com.lovepink.entity.User;
import com.lovepink.model.request.createUserRequest;

public interface UserService {
	User findById(String username);
	List<User> findAll();
	String deleteUser(String username);
	User createuser(createUserRequest req);

}
