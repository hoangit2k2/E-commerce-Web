package com.lovepink.controller;

import java.util.Set;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lovepink.entity.*;

/**
 * Lý do sử dụng "interface" vì mọi thứ trong "inteface" được định nghĩa là "static"
 */
public interface AdminController {

	// @formatter:off
	
	@RestController @CrossOrigin("*") @RequestMapping("api/users")
	public class RestUser extends AbstractRESTful<Users, String> {
		@Override protected String[] filesExist(Users e) {return new String[] {e.getImage()};}
		@Override protected void setFiles(Users e, Set<String> images) {
			Object image = images.toArray()[0];
			if(images.size()>0) e.setImage(image.toString());
		}
	}
	
	@RestController @CrossOrigin("*") @RequestMapping("api/contents")
	public class RestContents extends AbstractRESTful<Content, Integer> {
		@Override protected String[] filesExist(Content e) {
			Set<String> imgs = e.getContent_images();
			return imgs.toArray(new String[imgs.size()]);
		}
		@Override protected void setFiles(Content e, Set<String> images) {e.setContent_images(images);}
	}
	
	@RestController @CrossOrigin("*") @RequestMapping("api/categories")
	public class RestCategories extends AbstractRESTful<Category, Integer> {
		@Override protected String[] filesExist(Category e) {return new String[] {e.getImage()};}
		@Override protected void setFiles(Category e, Set<String> images) {
			Object image = images.toArray()[0];
			if(images.size()>0) e.setImage(image.toString());
		}
	}
	
	@RestController @CrossOrigin("*") @RequestMapping("api/orders")
	public class RestOrders extends AbstractRESTful<Orders, Integer> {
		@Override protected String[] filesExist(Orders e) {return new String[0];}
		@Override protected void setFiles(Orders e, Set<String> images){};
	}

	// @formatter:on
}
