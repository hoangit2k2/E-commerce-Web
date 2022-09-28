package rrs.model.entities;

import java.util.Date;
import java.util.Set;

import javax.persistence.CollectionTable;
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
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Table
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "CONTENTS")
public class Content {
	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id private Long id;
	private String subject;
	private String content;
	@DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
	@Column(name = "regtime")
	private Date regTime;
	private Integer views;
	private String status;
	private String localhost;
	private String contact;
	
	// Relationships
	@ElementCollection @Column(name = "image") @JoinTable(
		name = "CONTENT_IMAGES",
		joinColumns = @JoinColumn(name = "content_id")
	) private Set<String> content_images; // Content's images
		
	@ElementCollection @Column(name = "category_id")
	@CollectionTable(name = "CONTENT_TYPES", joinColumns = @JoinColumn(name = "content_id"))
	private Set<String> categories;
	
	@ManyToOne @JsonIncludeProperties({"username","name","email"}) @JoinColumn(name = "account_id")
	private Account account;

	public Content(Long id) {
		this.id = id;
	}
	
}
