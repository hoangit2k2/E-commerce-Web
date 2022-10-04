package rrs.model.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import rrs.utils.Random;

@Data
@Entity
@AllArgsConstructor
@Table(name = "CATEGORIES")
public class Category {
	
	@Id private String id;
	private String name;
	
	public Category() {
		id = Random.NumUppLow("", 8);
	}
}
