package com.lovepink.Dao;

import com.lovepink.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDao extends JpaRepository<Orders, Integer> {

}
