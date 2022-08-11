package edu.hoang.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name= "Students")
public class Student {
	@Id
	String email;
	String fullname;
	Double marks;
	Boolean gender;
	String country;
}
