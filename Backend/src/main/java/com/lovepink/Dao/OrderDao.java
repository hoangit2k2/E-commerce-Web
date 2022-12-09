package com.lovepink.Dao;

import com.lovepink.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderDao extends JpaRepository<Orders, Integer> {
    List<Orders> findByUsernameid(String usernameid);

}
