package edu.hoang.DAO;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.hoang.entity.OrderDetail;

public interface OrderDetailDAO extends JpaRepository<OrderDetail,Long> {
	
}
