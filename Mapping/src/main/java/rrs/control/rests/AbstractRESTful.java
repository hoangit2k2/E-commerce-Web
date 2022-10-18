package rrs.control.rests;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import rrs.model.utils.InterDAO;
import rrs.utils.CustomException;

/**
 * @param <E> is entity
 * @param <K> is the type of entity's key
 **/
public abstract class AbstractRESTful<E, K> {

	// @formatter:off
	@Autowired protected InterDAO<E, K> dao;

	@GetMapping({"","/{id}"}) // reading method to get data
	public ResponseEntity<Object> getData(@PathVariable(required = false) K id) throws IllegalArgumentException, CustomException {
		if(id!=null) { // get one by id or get all entities
			Optional<E> optional = dao.getOptional(id);
			return optional.isPresent()
					? ResponseEntity.ok(optional.get()) 
					: ResponseEntity.noContent().build();
		} else return ResponseEntity.ok(dao.getList());
	}
	
	@PostMapping({"","/{id}"}) // Post method to create entity
	public ResponseEntity<E> save(@RequestBody E entity) throws IllegalArgumentException, CustomException {
		return ResponseEntity.ok(dao.save(entity));
	}
	
	@PutMapping({"","/{id}"}) // Put method to update entity
	public ResponseEntity<E> update(@RequestBody E entity) throws IllegalArgumentException, CustomException	{
		return ResponseEntity.ok(dao.update(entity));
	}
	
	@DeleteMapping({"/{id}"}) // Delete method to remove entity
	public ResponseEntity<Void> delete(@PathVariable(required = false) K id) throws IllegalArgumentException, CustomException {
		if(id != null) {
			dao.remove(id);
			return ResponseEntity.ok().build();
		} else return ResponseEntity.noContent().build();
	}

	// @formatter:on
}
