package rrs.model.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
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
	
}
