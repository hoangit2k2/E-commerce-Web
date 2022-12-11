package com.lovepink.service.lmpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lovepink.Dao.categoryDao;
import com.lovepink.entity.Category;
import com.lovepink.exception.NotFoundException;
import com.lovepink.service.CategoryService;

import com.lovepink.model.request.createCategory;

@Service
public class CategoryServicelmpl implements CategoryService {
	@Autowired
	categoryDao dao;
	
	@Override
	public List<Category> finAll(){
		return dao.findAll();
	}
	@Override 
	public Category finById(Integer id) {
		List<Category> categorys = dao.findAll();
		for(Category category : categorys) {
			if(category.getId() == id) {
				return dao.findById(id).get();
			}
		}
		throw new NotFoundException("Id Không tồn tại");
	}
	@Override
	public String deleteCategory(Integer id) {
		List<Category> categorys = dao.findAll();
		for(Category category : categorys) {
			if(category.getId() == id) {
				dao.deleteById(id);
				return "Delete Sucssessfully";
			}
		}
		throw new NotFoundException("Id không tồn tại");
	} 
	@Override
	public Category createCategory(createCategory req ) {
		Category category = Category.toCategory(req);
		dao.save(category);
		return category;	
	}
	@Override
	public Category  updateCategory(createCategory req, int id) {
		List<Category> categorys = dao.findAll();
		for(Category category : categorys) {
			if(category.getId() == id) {
				category.setId(id);
				category.setName(req.getName());
				category.setImage(req.getImage());
				dao.save(category);
			}
		}
		throw new NotFoundException("Id không tồn tại");
	}
//	@InjectService
	public Category ok(createCategory c){
		return null;
	}
	
}
