package rrs.control.rests;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import rrs.model.entities.Content;
import rrs.model.utils.UpviewContent;

@RestController
@CrossOrigin(value = "*")
@RequestMapping(value = "/rest/contents")
public class RestContent extends AbstractRESTful<Content, Long> {
	@Autowired private UpviewContent optional;
	
	@Override @GetMapping({"","{id}","/upview/{id}"}) // reading method to get data and upload view += 1
	public ResponseEntity<Object> getData(@PathVariable(name = "id", required = false) Long id, HttpServletRequest req) {
		if(req.getRequestURI().contains("/upview") && id!=null && dao.getOptional(id).isPresent())
			ResponseEntity.ok(optional.upviews(id).get());
		return super.getData(id, req);
	}

}
