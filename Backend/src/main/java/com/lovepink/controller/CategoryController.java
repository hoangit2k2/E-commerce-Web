package com.lovepink.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.multipart.MultipartFile;

import com.lovepink.entity.Category;
import com.lovepink.exception.NotFoundException;
import com.lovepink.model.request.createCategory;
import com.lovepink.service.CategoryService;

import lombok.val;
import net.bytebuddy.implementation.bytecode.Throw;

@CrossOrigin("*")
@RestController
@RequestMapping("/rest/categorys")
public class CategoryController implements ServletContextAware{
	private ServletContext servletcontext;
	@Autowired
	CategoryService categoryService;
	@GetMapping("")
	public ResponseEntity<?> getListCategory(){
		List<Category> result = categoryService.finAll();
		return ResponseEntity.ok(result);
	}
	@GetMapping("{id}")
	public ResponseEntity<?> getcategorybyId(@PathVariable int id){
		Category result = categoryService.finById(id);
		return ResponseEntity.ok(result);
	}
	@DeleteMapping("{id}")
	public ResponseEntity<?> deleteCategory(@PathVariable int id){
		String result = categoryService.deleteCategory(id);
		return ResponseEntity.ok(result);
	}
	@PostMapping("")
	public ResponseEntity<?> createcategory(@RequestParam("files") MultipartFile[] files,@Valid @ModelAttribute createCategory req){
		try {
			String image = null;
			for(MultipartFile file : files) {
				image = file.getOriginalFilename();
				save(file);
			}
			req.setImage(image);
			Category category = categoryService.createCategory(req);
			return ResponseEntity.ok(category);
			
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}
	@PutMapping("/{id}")
	public ResponseEntity<?> updateCategory(@RequestParam("files") MultipartFile[] files, @Valid @ModelAttribute createCategory req, @PathVariable int id){
		try {
			
			String image = null;
			for(MultipartFile file : files) {
				image = file.getOriginalFilename();
				save(file);
			}
			req.setImage(image);
			Category category = categoryService.updateCategory(req,id);
			return ResponseEntity.ok(category);
			
		} catch (Exception e) {
			return null;
		}
	}
	
	private String save(MultipartFile file) {
		try {
		String filename = file.getOriginalFilename();
		byte[] bytes = file.getBytes();
		Path path = Paths.get(this.servletcontext.getRealPath("/uploads/imagecategory/" + filename));
		Files.write(path, bytes);
		return filename;
		} catch (Exception e) {
			return null;
		}
		
	}
	
	@Override
	public void setServletContext(ServletContext servletContext) {
		this.servletcontext = servletContext;
	}

}
