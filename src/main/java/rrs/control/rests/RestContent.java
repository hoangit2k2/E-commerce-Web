package rrs.control.rests;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import rrs.model.entities.Content;
import rrs.model.utils.SupportContent;

@RestController
@CrossOrigin(value = "*")
@RequestMapping(value = "/rest/contents")
public class RestContent extends AbstractRESTful<Content, Long> {
	
	@Autowired private SupportContent sc;
	
	// /upview/...
	@GetMapping("/upview/{id}") // reading method to get data and upload view += 1
	public ResponseEntity<Object> upview(@PathVariable Long id) throws Exception {
		return ResponseEntity.ok(sc.upviews(id).get());
	}
	
	// /active?a=true||false
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
	
	// /category?category_id=...
	@GetMapping("/category") // reading method to get data active or else get all
	public ResponseEntity<Object> getByCategory(@RequestParam(required = false) String category_id) {
		return ResponseEntity.ok(sc.getByCategoryId(category_id));
		
	}
	
	// /category?account_id=...
	@GetMapping("/account") // reading method to get data active or else get all
	public ResponseEntity<Object> getByAccount(@RequestParam(required = false) String account_id) {
		return ResponseEntity.ok(sc.getByAccountId(account_id));		
	}
}
