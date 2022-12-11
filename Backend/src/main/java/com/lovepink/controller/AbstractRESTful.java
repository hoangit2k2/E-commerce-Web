package com.lovepink.controller;

import java.security.Principal;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.lovepink.Dao.InterDAO;
import com.lovepink.service.FileUpload;

/**
 * @param <E> is entity
 * @param <K> is the type of entity's key
 **/
public abstract class AbstractRESTful<E, K> {

	// @formatter:off
	@Autowired protected InterDAO<E, K> dao;
	@Autowired protected HttpServletRequest req;
	@Autowired protected FileUpload file;
	
	protected abstract String[] filesExist(E e);
	protected abstract void setFiles(E e, Set<String> images);

	@GetMapping({"","/{id}"}) // reading method to get data
	public ResponseEntity<Object> getData(@PathVariable(required = false) K id)
	throws IllegalArgumentException {
		if(id!=null) { // get one by id or get all entities
			Optional<E> optional = dao.getOptional(id);
			return optional.isPresent()
					? ResponseEntity.ok(optional.get()) 
					: ResponseEntity.noContent().build();
		} else return ResponseEntity.ok(dao.getList());
	}
	
	@PostMapping({"","/{id}"}) // Post method to create entity
	public ResponseEntity<E> save(E entity, @RequestParam(required = false) String dir, MultipartFile[] files)
	throws IllegalArgumentException {
		return this.update(dao.save(entity), dir, files);
	}
	
	@PutMapping(value = {"","/{id}"}) // Put method to update entity
	public ResponseEntity<E> update(E entity, @RequestParam(required = false) String dir, MultipartFile[] files)
	throws IllegalArgumentException	{
		this.saveFile(entity, dir, files);
		return ResponseEntity.ok(dao.update(entity));
	}
	
	@DeleteMapping({"/{id}"}) // Delete method to remove entity
	public ResponseEntity<Void> delete(@PathVariable(required = false) K id, @RequestParam(required = false) String dir) 
	throws IllegalArgumentException {
		if(id != null) {
			Optional<E> optional = dao.getOptional(id);
			if(optional.isPresent()) file.deleteFiles(this.filesExist(optional.get()), dir); // delete all old files
			dao.remove(id);
			return ResponseEntity.ok().build();
		} else return ResponseEntity.noContent().build();
	}
	

	// ---------------------------------------------------------- SUPPORT METHODS
	
	// get user is logged
	protected String getUser(String defaultUser) {
		Principal p = req.getUserPrincipal();
		return p == null ? defaultUser : p.getName();
	}
	
	// save file to project
	protected String[] saveFile(E e, String dir, MultipartFile... files) {
		if (e == null || files == null) return null;
		K id = dao.getId(e);
		Optional<E> optional = dao.getOptional(id); // get old entity
		
		if(optional.isPresent()) file.deleteFiles(this.filesExist(optional.get()), dir); // delete all old files
		if (this.filesExist(e).length > 0) { // add new all files
			Set<String> images = new HashSet<>();
			for (MultipartFile f : files) images.add(
				file.saveFile(fileHashName(id, System.currentTimeMillis(), f.getOriginalFilename()), f, dir)
			);
			this.setFiles(e, images);
		} return this.filesExist(e);
	}
	
	// hash code array String EX: ["a", 1, 3, x.jpg] => "2761525322623523.jpg"
	private String fileHashName(Object... names) {
		StringBuilder str = new StringBuilder();
		String type = names[names.length - 1].toString();
		type = type.substring(type.lastIndexOf(".")).trim();
		for (Object name : names) str.append(name);
		return str.hashCode() + type;
	}
	
	// @formatter:on

}