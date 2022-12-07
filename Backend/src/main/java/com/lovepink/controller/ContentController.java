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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.multipart.MultipartFile;

import com.lovepink.entity.Content;
import com.lovepink.exception.NotFoundException;
import com.lovepink.model.request.createContenRequest;
import com.lovepink.model.request.createUserRequest;
import com.lovepink.service.ContentService;

import net.bytebuddy.implementation.bytecode.Throw;
@CrossOrigin("*")
@RestController
//@RequestMapping("/rest/contents")
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
//	@PreAuthorize("hasRole('admin')")
	@GetMapping("/rest/contents")
	public ResponseEntity<?> getListConten(){
		List<Content> content = contenService.findAll();
		return  ResponseEntity.ok(content);
	}
	
	@GetMapping("/rest/contents/{id}")
	public ResponseEntity<?> getContenById(@PathVariable int id){
		Content result = contenService.findById(id);
		return ResponseEntity.ok(result);
	}
	@DeleteMapping("/rest/contents/{id}")
	public ResponseEntity<?> deleteContent(@PathVariable int id){
		String result = contenService.deleteUser(id);
		return ResponseEntity.ok(result);	
	}
	@PreAuthorize("hasRole('cust')")
	@PostMapping("/rest/contents")
	public ResponseEntity<?> createContent(@RequestParam("files") MultipartFile[] files, @Valid @ModelAttribute createContenRequest req 
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
	@PutMapping("/rest/contents/{id}")
	public ResponseEntity<?> updateContent(@RequestParam("files") MultipartFile[] files, @Valid @ModelAttribute createContenRequest req, @PathVariable int id){
		try {
			Set<String> setA = new HashSet<String>();

			for(MultipartFile file : files) {
				setA.add(file.getOriginalFilename());
				save(file);
				}
			if(setA.toArray().length == 0){

			}
			else {
				req.setContent_images(setA);
			}
			Content result = contenService.updatecontent(req, id);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}
	@GetMapping("/rest/contents/username/{username}")
	public ResponseEntity<?> getContentByusername(@PathVariable String username){
		List<Content> result = contenService.findContentId(username);
		return  ResponseEntity.ok(result);
	}
	@GetMapping("/rest/contents/category/{categoryid}")
		public ResponseEntity<?> getContentByCategoryId(@PathVariable int categoryid){
			List<Content> result = contenService.findContentByCategory(categoryid);
			return ResponseEntity.ok(result);
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
