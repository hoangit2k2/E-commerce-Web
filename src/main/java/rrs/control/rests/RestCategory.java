package rrs.control.rests;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import rrs.model.entities.Category;
import rrs.model.utils.SaveWithRandomId;
@RestController
@CrossOrigin(value = "*")
@RequestMapping(value = "/rest/categories")
public class RestCategory extends AbstractRESTful<Category, String> {
	
	@Autowired private SaveWithRandomId<Category> saveRandomId;
	@Autowired private HttpServletRequest req;
	
	@PostMapping({"/random"}) // Post method to create entity
	public ResponseEntity<Category> save(@RequestBody Category entity) {
		int i = req.getRequestURI().lastIndexOf("/random");
		return i>-1 ? ResponseEntity.ok(saveRandomId.save(entity, true)) : super.save(entity);
	}
	
}
