package rrs.control.rests;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import rrs.model.entities.Category;
import rrs.model.utils.SaveWithRandomId;
@RestController
@CrossOrigin(value = "*")
@RequestMapping(value = "/rest/categories")
public class RestCategory extends AbstractRESTful<Category, String>{
	@Autowired private SaveWithRandomId<Category> saveRandomId;
	
	@PostMapping({"/random"}) // Post method to create entity
	public ResponseEntity<Category> save(@RequestBody Category entity) {
		entity = saveRandomId.save(entity, true);
		return ResponseEntity.ok(entity);
	}
	
}
