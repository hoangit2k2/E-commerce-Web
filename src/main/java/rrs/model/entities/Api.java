package rrs.model.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@Table
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "APIs")
public class Api {
	@Id @NonNull private String id;
	private String value;
}
