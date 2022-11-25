package com.lovepink.controller;

import java.util.List;

import javax.servlet.ServletContext;
import javax.validation.Valid;

import com.lovepink.entity.Users;
import com.lovepink.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.ServletContextAware;
import com.lovepink.model.request.createUserRequest;
import com.lovepink.model.request.updatepassword;
import com.lovepink.service.UserService;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin("*")
@RestController
@RequestMapping("/rest/users")
@Slf4j
public class UserController implements ServletContextAware{
	private ServletContext servletcontext;
	@Autowired
	UserService userService;
	
	
//	@PreAuthorize("hasRole('admin')")
	@GetMapping("")
	public ResponseEntity<?> getListUser(){
		List<Users> user = userService.findAll();
		return ResponseEntity.ok(user);
	}
//	@PreAuthorize("hasRole('cust')")
	@GetMapping("{username}")
	public ResponseEntity<?> getUserById(@PathVariable String username){
		Users result = userService.findById(username);
		return ResponseEntity.ok(result);
	}
	@PostMapping("")	
	public ResponseEntity<?> createUser(@Valid @RequestBody createUserRequest req){
		Users result = userService.createuser(req);
		System.out.println(result);
		return ResponseEntity.ok(result);
	}
	@DeleteMapping("{username}")
	public ResponseEntity<?> deleteUser(@PathVariable String username){
		String result = userService.deleteUser(username);
		return ResponseEntity.ok(result);
	}
	@PutMapping("/{username}")
	public ResponseEntity<?> updateUser(@PathVariable String username, @RequestBody createUserRequest req){
		try {
			Users user = userService.updateuser(req, username);
		System.out.println(user);
			return ResponseEntity.ok(user);
		}catch (Exception e){
			throw new NotFoundException("Xay ra loi");
		}
	}
	@PutMapping("/{username}/password")
	public ResponseEntity<?> updatePassword(@PathVariable String username, @RequestBody createUserRequest req){
		try {
			Users user = userService.updatepassword(req, username);
			return ResponseEntity.ok(user);
		}catch (Exception e){
		throw new NotFoundException("Xay ra loi");
		}
	}
	@Override
	public void setServletContext(ServletContext servletContext) {
			this.servletcontext = servletContext;
	}
}
