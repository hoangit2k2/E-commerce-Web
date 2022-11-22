package com.lovepink.controller;

import java.util.List;

import javax.servlet.ServletContext;
import javax.validation.Valid;

import com.lovepink.entity.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.ServletContextAware;

import com.lovepink.model.request.createUserRequest;
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
	
	
	@PreAuthorize("hasRole('admin')")
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
		return ResponseEntity.ok(result);
	}
	@DeleteMapping("{username}")
	public ResponseEntity<?> deleteUser(@PathVariable String username){
		String result = userService.deleteUser(username);
		return ResponseEntity.ok(result);
	}	
	@Override
	public void setServletContext(ServletContext servletContext) {
			this.servletcontext = servletContext;
	}
}
