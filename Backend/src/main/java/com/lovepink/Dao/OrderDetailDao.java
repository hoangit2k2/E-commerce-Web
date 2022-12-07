package com.lovepink.Dao;

import com.lovepink.entity.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailDao extends JpaRepository<OrderDetails, Integer> {
}
