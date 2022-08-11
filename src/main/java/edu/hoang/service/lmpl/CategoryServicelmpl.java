package edu.hoang.service.lmpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.hoang.DAO.CategoryDAO;
import edu.hoang.entity.Category;
import edu.hoang.service.CategoryService;
@Service
public class CategoryServicelmpl implements CategoryService{

	@Autowired
	CategoryDAO cdao;
	

	@Override
	public List<Category> findAll(){
		return cdao.findAll();
	}
}
