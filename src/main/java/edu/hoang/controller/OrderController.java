package edu.hoang.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class OrderController {
	
	@RequestMapping("/order/checkout")
	public String Checkout() {
		return "order/checkout";
	}
	
	@RequestMapping("/order/list")
	public String List() {
		return "order/list";
	}
	
	@RequestMapping("/order/detail")
	public String Detail() {
		return "order/detail";
	}
}
