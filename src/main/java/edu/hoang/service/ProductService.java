package edu.hoang.service;

import java.util.List;
import java.util.Optional;

import edu.hoang.entity.Product;

public interface ProductService {
	List<Product>findAll();
	Product findById(Integer id);
	List<Product> findByCategory(String string);
}
