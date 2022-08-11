package edu.hoang.service.lmpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.hoang.DAO.ProductDAO;
import edu.hoang.entity.Product;
import edu.hoang.service.ProductService;

@Service
public class ProductServicelmpl implements ProductService {
	@Autowired 
	ProductDAO dao;

	@Override
	public List<Product> findAll() {
		return dao.findAll();
	}

	@Override
	public Product findById(Integer id) {
		return dao.findById(id).get();
	}

	@Override
	public List<Product> findByCategory(String cid) {
		return dao.findByCategory(cid);
	}

}
