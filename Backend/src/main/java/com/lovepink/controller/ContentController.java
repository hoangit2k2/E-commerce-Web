package com.lovepink.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.multipart.MultipartFile;

import com.lovepink.entity.Content;
import com.lovepink.model.request.createUserRequest;
import com.lovepink.service.ContentService;
@CrossOrigin("*")
@RestController
@RequestMapping("/rest/contents")
public class ContentController implements ServletContextAware {
	private ServletContext servletcontext;
	@Autowired
	ContentService contenService;
	
	@GetMapping("/search")
	public ResponseEntity<?> searchUser(@RequestParam(name = "keyword", required = false, defaultValue = "") String name){
		System.out.println(name);
		List<Content> result = contenService.searchContent(name);
		return ResponseEntity.ok(result);
		}
	
	@GetMapping("")
	public ResponseEntity<?> getListConten(){
		List<Content> content = contenService.findAll();
		return ResponseEntity.ok(content);
	}
	
	@GetMapping("{id}")
	public ResponseEntity<?> getContenById(@PathVariable int id){
		Content result = contenService.findById(id);
		return ResponseEntity.ok(result);
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<?> deleteContent(@PathVariable int id){
		String result = contenService.deleteUser(id);
		return ResponseEntity.ok(result);	
	}
	
	@PostMapping("")
	public ResponseEntity<?> createContent(@RequestParam("files") MultipartFile[] files, @Valid @ModelAttribute createUserRequest req 
											){
		try {
			Set<String> setA = new HashSet<String>();
			for(MultipartFile file : files) {
		        setA.add(file.getOriginalFilename());
				save(file);
			}
	        System.out.println(setA);
			req.setContent_images(setA);
			Content result = contenService.createUser(req);
			return ResponseEntity.ok(result);	
		} catch (Exception e) {
			return null;
		}
	}
	@PutMapping("/{id}")
	public ResponseEntity<?> updateContent(@RequestParam("files") MultipartFile[] files, @Valid @ModelAttribute createUserRequest req, @PathVariable int id){
		try {
			Set<String> setA = new HashSet<String>();
			for(MultipartFile file : files) {
				setA.add(file.getOriginalFilename());
				save(file);
			}
			req.setContent_images(setA);
			Content result = contenService.updatecontent(req, id);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}
	
	
	private String save(MultipartFile file) {
		try {
		String filename = file.getOriginalFilename();
		byte[] bytes = file.getBytes();
		Path path = Paths.get(this.servletcontext.getRealPath("/uploads/imagecontent/" + filename));
		Files.write(path, bytes);
		return filename;
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return null;
	}
	
	@Override
	public void setServletContext(ServletContext servletContext) {
		this.servletcontext = servletContext;
		
	}
}
