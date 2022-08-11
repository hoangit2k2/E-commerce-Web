package edu.hoang.DAO;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.hoang.entity.Category;

public interface CategoryDAO extends JpaRepository<Category,String> {
	
}
