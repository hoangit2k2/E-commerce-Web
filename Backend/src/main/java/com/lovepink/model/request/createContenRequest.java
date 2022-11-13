package com.lovepink.model.request;

import java.util.Set;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class createContenRequest {
	
	private Integer categoryid;
	private String usernameid;
	@NotNull(message = "NameContent is required")
	@NotEmpty(message = "NameContent is required")
	@JsonProperty("namecontent")
	private String namecontent;
	@NotNull(message = "Subject is required")
	@NotEmpty(message = "subject")
	private String subject;
	private Integer price;
	private String email;
	private String phone;
	private String address;
	private boolean status;
	private Set<String> content_images; // Content's images
}
