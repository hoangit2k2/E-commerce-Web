package com.lovepink.service;

import java.util.List;

import com.lovepink.entity.Category;
import com.lovepink.model.request.createCategory;

public interface CategoryService {

	List<Category> finAll();

	Category finById(Integer id);

	String deleteCategory(Integer id);

	Category createCategory(createCategory req);

	Category updateCategory(com.lovepink.model.request.createCategory req, int id);

}
