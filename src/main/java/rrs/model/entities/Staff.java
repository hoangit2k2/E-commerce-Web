package rrs.model.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Table
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "STAFFS")
public class Staff {
	
	@Id private String username;
	private String password;
	private String email;
	private Boolean role;
	private String image;
}
