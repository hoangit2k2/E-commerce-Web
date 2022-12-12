package com.lovepink.controller;
import java.lang.reflect.Field;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.lovepink.Dao.StatisticRepository.PROCEDURES;
import com.lovepink.entity.*;
import com.lovepink.service.SQLService;

/**
 * Lý do sử dụng "interface" vì mọi thứ trong "inteface" được định nghĩa là
 * "static"
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
		@Override protected void setFiles(Orders e, Set<String> images){}
		@Override public ResponseEntity<Orders> update(@RequestBody Orders entity, String dir, MultipartFile[] files
		) throws IllegalArgumentException {
			entity.getOrderDetails().forEach(x -> x.setOrders(new Orders(entity.getId())));
			return ResponseEntity.ok(super.dao.update(entity));
		}
		
	}
	
	@RestController @CrossOrigin("*") @RequestMapping("api/statistic")
	public class RestStatistic {
		@Autowired private SQLService dao;
		@RequestMapping({"","/"})
		public ResponseEntity<Object> execute(
				@RequestParam(required = false) PROCEDURES proc, 
				@RequestParam(required = false) Object...o) 
		throws Exception {
			return ResponseEntity.ok(proc!=null?dao.execute(proc, o):getField());
		}
		
		private String getField() {
			Field[] fields = PROCEDURES.class.getFields();
			StringBuilder str = new StringBuilder("<div style=\"color: white; background: black; height: 100vh;\">");
			str.append(PROCEDURES.class.toString());
			for(Field f : fields) str.append("<br><a href=\"").append("?proc=").append(f.getName()).append("\">")
			.append(f.getName()).append("<//a>");
			return str.append("<//div>").toString();
		}
	}

	// @formatter:on
}
