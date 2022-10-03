package rrs.control.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class ContentController {
	
	@GetMapping("/content")
	public String getContent() {
		return  "/content/list" ;
	}
}
