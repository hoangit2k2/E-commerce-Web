package rrs.control.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import rrs.model.entities.Content;
import rrs.model.services.ContentService;


@Controller
public class ContentController {
	@Autowired
	ContentService contentservice;
	@GetMapping("/content")
	public String getContent() {
		return  "/content/list" ;
	}
	@RequestMapping("/content/{id}")
	public String detailProduct(Model model,@PathVariable("id") Integer id ) {
//		System.out.println(id);
		return "content/detail";
	}
}
