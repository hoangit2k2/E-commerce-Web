package com.lovepink.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.ServletContext;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin("*")
@RestController
@RequestMapping("/rest/uploadfile")
public class UploadFileController implements ServletContextAware {
	private ServletContext servletcontext;
	@PostMapping("")
	public ResponseEntity<Void> uploadFile(@RequestParam("files") MultipartFile[] files){
		try {
			Set<String> setA = new HashSet<String>();
			for(MultipartFile file : files) {
		        setA.add(file.getOriginalFilename());
				System.out.println(file);

				save(file);
			}
	        System.out.println(setA);
 
			return new ResponseEntity<Void>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		}
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
