package rrs.model.entities;

import java.sql.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Table
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "ACCOUNTS")
public class Account {
	
	@Id
	private String username;
	private String password;
	private String name;
	private String email;
	private String image;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "regdate")
	private Date regDate;

	// Relationships
	@JsonIgnore @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
	private List<Content> contents;
	
	@ElementCollection @Column(name = "content_id")
	@CollectionTable(name = "LIKES", joinColumns = @JoinColumn(name = "account_id"))
	private Set<Long> likes;
	
	public Account(String username) {
		this.username = username;
	}
	
	
}
