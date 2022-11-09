package com.lovepink.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.springframework.stereotype.Component;

import com.lovepink.model.request.createCategory;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Table
@NoArgsConstructor
@AllArgsConstructor
@Component
@Entity(name = "category")
public class Category {
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private Integer id;
	private String name;
	private String image;

	public static Category toCategory(createCategory req) {
		Category category = new Category();
		category.setId(req.getId());
		category.setName(req.getName());
		category.setImage(req.getImage());
		return category;
	}
}
