package rrs.model.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
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
public class Like {

	@EmbeddedId private LikeId id;
	@Builder.ObtainVia
	@Column(name = "exetime")
	@DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
	private Date exeTime = new Date();
	
	public Like(LikeId id) {
		super();
		this.id = id;
	}
	
	
}
