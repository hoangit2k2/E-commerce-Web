package com.lovepink.entity;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.lovepink.model.request.createUserRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Table
@NoArgsConstructor
@AllArgsConstructor
@Component
@Entity(name = "CONTENTS")
public class Content {
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private Integer id;
	private Integer customerid;
	private Integer categoryid;
	@NotNull
	private String namecontent;
	private String subject;
	private Integer price;
	private String email;
	private String phone;
	private String address;
	@DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
	@Column(name = "datetime")
	private LocalDateTime datetime;
	private Boolean status;
	@ElementCollection
	@Column(name = "image")
	@JoinTable(name = "image", joinColumns = @JoinColumn(name = "contentid"))
	//image
	private Set<String> content_images;
	static final Path CURRENT_FOLDER = Paths.get(System.getProperty("user.dir"));
	public static Content toContent(createUserRequest req)  {
		Content content = new Content();
		content.setCustomerid(req.getCustomerid());
		content.setCategoryid(req.getCategoryid());
		content.setNamecontent(req.getNamecontent());
		content.setSubject(req.getSubject());
		content.setPrice(req.getPrice());
		content.setEmail(req.getEmail());
		content.setPhone(req.getPhone());
		content.setAddress(req.getAddress());
		content.setDatetime(java.time.LocalDateTime.now());
		content.setStatus(req.isStatus());		
		
		//update Image
//		Path staticPath = Paths.get("static");
//		Path imagePath = Paths.get("images");
//		MultipartFile[] image =  (MultipartFile[]) req.getContent_images();
//		if(!Files.exists(CURRENT_FOLDER.resolve(staticPath).resolve(imagePath))) {
//			Files.createDirectories(CURRENT_FOLDER.resolve(staticPath).resolve(imagePath));
//		}
//		Path file = CURRENT_FOLDER.resolve(staticPath).resolve(imagePath).resolve(image.getOriginalFilename());
//		try(OutputStream os = Files.newOutputStream(file)) {
//			os.write(image.getBytes());
//		} catch (Exception e) {
//			System.out.println(e);
//		}
		
		content.setContent_images(req.getContent_images());
		return content;
	}
}
