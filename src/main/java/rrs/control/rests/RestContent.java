package rrs.control.rests;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import rrs.model.entities.Content;
import rrs.model.services.ContentService;
import rrs.model.utils.SupportContent;

@RestController
@CrossOrigin(value = "*")
@RequestMapping(value = "/rest/contents")
public class RestContent extends AbstractRESTful<Content, Long> {
	
	@Autowired private SupportContent sc;
	
	@GetMapping("/upview/{id}") // reading method to get data and upload view += 1
	public ResponseEntity<Object> upview(@PathVariable Long id) throws Exception {
		return ResponseEntity.ok(sc.upviews(id).get());
	}
	
	@GetMapping("/active") // reading method to get data active or else get all
	public ResponseEntity<Object> upview(@RequestParam(required = false) Object a) {
		try {
			String a1 = a.toString();
			a1 = a1.length() < 1 ? "true" : a1.equalsIgnoreCase("true") ? a1 : "false";
			return ResponseEntity.ok(sc.getByActive(Boolean.parseBoolean(a1)));
		} catch (Exception e) {
			return ResponseEntity.ok(sc.getByActive(null));
		}
	}
	
	@GetMapping("/category") // reading method to get data active or else get all
	public ResponseEntity<Object> getByCategory(@RequestParam(required = false) String category_id) {
		return ResponseEntity.ok(sc.getByCategoryId(category_id));
		
	}
	
	@GetMapping("/account") // reading method to get data active or else get all
	public ResponseEntity<Object> getByAccount(@RequestParam(required = false) String account_id) {
		return ResponseEntity.ok(sc.getByAccountId(account_id));		
	}
	
	// _____________________________________________________________________ LIKES READ - DELETE
	@GetMapping("/likes") // reading method to get data active or else get all
	public ResponseEntity<Object> getLikes(@RequestParam(required = false) Long c) {
		return ResponseEntity.ok(((ContentService) sc).getLikes(c));		
	}

	@DeleteMapping("/likes") // delete like by content_id
	public ResponseEntity<Integer> deleteLikes(@RequestParam(required = false) Long c) {
		int quantity = ((ContentService) sc).deleteLikes(c);
		return c > 0 ? ResponseEntity.ok(quantity) : ResponseEntity.noContent().build();
	}
}
