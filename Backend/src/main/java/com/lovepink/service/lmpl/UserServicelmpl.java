package com.lovepink.service.lmpl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lovepink.Dao.userDao;
import com.lovepink.entity.User;
import com.lovepink.exception.NotFoundException;
import com.lovepink.model.request.createUserRequest;
import com.lovepink.service.UserService;

@Service
public class UserServicelmpl implements UserService {
	@Autowired
	userDao dao;
	@Override
	public User findById(String username) {
		return dao.findById(username).get();	
	}
	@Override
	public List<User> findAll(){
		return dao.findAll();
	}
	@Override
	public String deleteUser(String username) {
//		List<User> users = dao.findAll();
//		for(User user : users) {
//			if(user.getUsername() == username) {
//				dao.deleteById(username);
//				return "Delete user Successfully";
//			}
//		}
//		throw new NotFoundException("Username không tồn tại");
		dao.deleteById(username);
		return "Delete user Successfully";
	}
	@Override
	public User createuser(createUserRequest req) {
			User user = User.toUser(req);
			dao.save(user);
			return user;
	
	}
	
} 
