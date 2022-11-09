package com.lovepink.entity;
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
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.lovepink.model.request.createContenRequest;
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
//	private String username;
	private Integer categoryid;
	@NotNull
	private String usernameid;
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
	private Set<String> content_images;
	
//	@ManyToOne @JsonIncludeProperties({"username","name","email","image"})
//	@JoinColumn(name = "usernameid")
//	private User user;
	static final Path CURRENT_FOLDER = Paths.get(System.getProperty("user.dir"));

	//tocontent upadte api
	public static Content toContent(createContenRequest req)  {
		Content content = new Content();
		content.setUsernameid(req.getUsernameid());
		content.setCategoryid(req.getCategoryid());
		content.setNamecontent(req.getNamecontent());
		content.setSubject(req.getSubject());
		content.setPrice(req.getPrice());
		content.setEmail(req.getEmail());
		content.setPhone(req.getPhone());
		content.setAddress(req.getAddress());
		content.setDatetime(java.time.LocalDateTime.now());
		content.setStatus(req.isStatus());		
		content.setContent_images(req.getContent_images());
		return content;
	}
}
