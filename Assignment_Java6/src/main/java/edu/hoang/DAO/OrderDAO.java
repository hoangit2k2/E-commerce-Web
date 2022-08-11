package edu.hoang.DAO;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.hoang.entity.Order;

public interface OrderDAO extends JpaRepository<Order,Long> {
	
}
