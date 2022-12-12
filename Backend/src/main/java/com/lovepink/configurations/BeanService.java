package com.lovepink.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.lovepink.Dao.InterDAO;
import com.lovepink.entity.*;
import com.lovepink.service.AbstractService;

@Configuration
public class BeanService {

	@Bean // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ USERS
	public InterDAO<Users, String> UserService() {
		return new AbstractService<Users, String>() {
			@Override
			public String getId(Users entity) {
				return entity.getUsername();
			};
		};
	}

	@Bean // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ CATEGORIES
	public InterDAO<Category, Integer> categoryService() {
		return new AbstractService<Category, Integer>() {
			@Override
			public Integer getId(Category entity) {
				return entity.getId();
			};
		};
	}

	@Bean // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ CONTENTS
	public InterDAO<Content, Integer> ContentService() {
		return new AbstractService<Content, Integer>() {
			@Override
			public Integer getId(Content entity) {
				return entity.getId();
			};
		};
	}
	
	@Bean // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ ORDERS
	public InterDAO<Orders, Integer> OrderService() {
		return new AbstractService<Orders, Integer>() {
			@Override
			public Integer getId(Orders entity) {
				return entity.getId();
			};
		};
	}
	
	@Bean // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ ORDER_DETAILS
	public InterDAO<OrderDetails, Integer> OrderDetailService() {
		return new AbstractService<OrderDetails, Integer>() {
			@Override
			public Integer getId(OrderDetails entity) {
				return entity.getID();
			};
		};
	}

}
