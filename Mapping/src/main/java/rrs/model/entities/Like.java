package rrs.model.entities;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "LIKES")
@IdClass(Like.class)
public class Like implements Serializable {

	private static final long serialVersionUID = -6077393952170790904L;
	@Id private String account_id;
	@Id private Long content_id;
	@Builder.ObtainVia
	@Column(name = "exetime")
	@DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
	private Date exeTime = new Date(System.currentTimeMillis());
	
	public Like(String account_id, Long content_id) {
		this.account_id = account_id;
		this.content_id = content_id;
	}
	
	
}
