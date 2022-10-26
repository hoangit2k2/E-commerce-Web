package rrs.control.rests;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import rrs.model.entities.Content;
import rrs.model.services.ContentService;

@RestController
@CrossOrigin(value = "*")
@RequestMapping(value = "/rest/contents")
public class RestContent extends AbstractRESTful<Content, Long> {
	
	// /upview/...
	@GetMapping("/upview/{id}") // reading method to get data and upload view += 1
	public ResponseEntity<Object> upview(@PathVariable Long id) throws Exception {
		ContentService cs = (ContentService) super.dao;
		return ResponseEntity.ok(cs.upviews(id).get());
	}
	
	// /active?a=true||false
	@GetMapping("/active") // reading method to get data active or else get all
	public ResponseEntity<Object> upview(@RequestParam(required = false) Object a) {
		ContentService cs = (ContentService) super.dao;
		try {
			String a1 = a.toString();
			a1 = a1.length() < 1 ? "true" : a1.equalsIgnoreCase("true") ? a1 : "false";
			return ResponseEntity.ok(cs.getByActive(Boolean.parseBoolean(a1)));
		} catch (Exception e) {
			return ResponseEntity.ok(cs.getByActive(null));
		}
	}
	
	// /category?category_id=...
	@GetMapping("/category") // reading method to get data active or else get all
	public ResponseEntity<Object> getByCategory(@RequestParam(required = false) String category_id) {
		ContentService cs = (ContentService) super.dao;
		return ResponseEntity.ok(cs.getByCategoryId(category_id));
		
	}
	
	// /category?account_id=...
	@GetMapping("/account") // reading method to get data active or else get all
	public ResponseEntity<Object> getByAccount(@RequestParam(required = false) String account_id) {
		ContentService cs = (ContentService) super.dao;
		return ResponseEntity.ok(cs.getByAccountId(account_id));		
	}
}
