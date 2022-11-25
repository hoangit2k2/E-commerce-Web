package com.lovepink.service.lmpl;
import java.util.List;
import java.util.Optional;

import com.lovepink.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.User;
import com.lovepink.Dao.userDao;
import com.lovepink.entity.Users;
import com.lovepink.model.request.createUserRequest;
import com.lovepink.model.request.updatepassword;
import com.lovepink.service.UserService;

@Service
public class UserServicelmpl implements UserService {
	@Autowired
	userDao dao;
	@Autowired
	BCryptPasswordEncoder pe;
	@Override
	public Users findById(String username) {
		return dao.findById(username).get();
	}
	@Override
	public List<Users> findAll(){
		return dao.findAll();
	}
	@Override
	public String deleteUser(String username) {
		dao.deleteById(username);
		return "Delete user Successfully";
	}
	@Override
	public Users createuser(createUserRequest req) {
			Users user = Users.toUser(req);
		System.out.println(user);
			dao.save(user);
			return user;
	}
//	@Override
//	public Users findbyid(String username){
//	List<Users> users = dao.findAll();
//	for(Users user : users){
//		if(user.getUsername() == username){
//			return dao.findById(username).get();
//		}
//	}
//	throw  new NotFoundException("Username Không tồn tại");
//	}

	@Override
	public Users updateuser(createUserRequest req, String username){
        try {
			Users user = new Users();
			Optional<Users> users = dao.findById(username);
					user.setUsername(users.get().getUsername());
					user.setPassword(users.get().getPassword());
					user.setRole(users.get().getRole());
					user.setName(req.getName());
					user.setEmail(req.getEmail());
					user.setPhone(req.getPhone());
					user.setAddress(req.getAddress());
					user.setImage(req.getImage());
					System.out.println(user);
					dao.save(user);
            } catch (Exception e){
			throw new NotFoundException("ID Khong ton tai");
		}
		return null;
	}

	@Override
	public Users updatepassword(createUserRequest req, String username){
		try {
			Users user = new Users();
			Optional<Users> users = dao.findById(username);
			user.setUsername(users.get().getUsername());
			user.setPassword(req.getPassword());
			user.setRole(users.get().getRole());
			user.setName(users.get().getName());
			user.setEmail(users.get().getEmail());
			user.setPhone(users.get().getPhone());
			user.setAddress(users.get().getAddress());
			user.setImage(users.get().getImage());
			dao.save(user);
		} catch (Exception e){
			throw new NotFoundException("ID Khong ton tai");
		}
		return null;
	}
}
