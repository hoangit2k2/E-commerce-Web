package com.lovepink.Dao;

import com.lovepink.entity.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderDetailDao extends JpaRepository<OrderDetails, Integer> {
    List<OrderDetails> findByOrders_Id(Integer id);

}
