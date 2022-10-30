package rrs.model.entities;

import java.io.Serializable;

import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class LikeId implements Serializable {
	
	private static final long serialVersionUID = 608162775959528444L;
	private String account_id;
	private Long content_id;
}