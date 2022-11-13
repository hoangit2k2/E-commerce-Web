package com.lovepink.service.lmpl;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.User;
import com.lovepink.Dao.userDao;
import com.lovepink.entity.Users;
import com.lovepink.model.request.createUserRequest;
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
	public Users createuser(createUserRequest req) {
			Users user = Users.toUser(req);
			dao.save(user);
			return user;
	}
}
