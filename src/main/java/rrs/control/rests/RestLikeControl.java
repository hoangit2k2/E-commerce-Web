package rrs.control.rests;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import rrs.model.entities.Like;
import rrs.model.services.LikeService;
import rrs.utils.CustomException;

@RestController
@CrossOrigin("*")
@RequestMapping("/rest/likes")
public class RestLikeControl extends AbstractRESTful<Like, Like>{
	
	// /likes/c?c=...(c is content_id)
	@GetMapping("/c") // reading method to get data active or else get all
	public ResponseEntity<Object> getLikesByContentId(@RequestParam(required = false) Long c) {
		return ResponseEntity.ok(((LikeService) super.dao).getLikes(c));		
	}

	// /likes/id?account_id=...&content_id=...
	@DeleteMapping("/id") // delete like by content_id
	public ResponseEntity<Void> deleteLikeById(
			@RequestParam(required = false) String account_id,
			@RequestParam(required = false) Long content_id
	) throws IllegalArgumentException, CustomException {
		return super.delete(new Like(account_id, content_id));
	}

	// /likes/content_id?content_id=...
	@DeleteMapping("/content_id") // delete like by content_id
	public ResponseEntity<Integer> deleteLikesByContentId(@RequestParam(required = false) Long content_id) {
		int quantity = ((LikeService) super.dao).deleteLikes(content_id);
		return content_id > 0 ? ResponseEntity.ok(quantity) : ResponseEntity.noContent().build();
	}
}
