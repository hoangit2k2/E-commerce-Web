package rrs.control.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({"/rrs"})
public class PageControl {

	// @formatter:off
 
	@GetMapping({"" ,"/{pages}", "/{pages}/{file}"}) public String getPage(
			@PathVariable(value = "pages", required = false) String pages,
			@PathVariable(value = "file", required = false) String file
		) {
		return (pages==null && file==null) ? "/pages/home" : '/'+pages+'/'+file;
	}
	
	// @formatter:on
}
