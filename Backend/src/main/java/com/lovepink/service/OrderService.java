package com.lovepink.service;
import com.fasterxml.jackson.databind.JsonNode;
import com.lovepink.entity.Orders;
import com.lovepink.model.request.createOrderRequest;

import java.util.List;


public interface OrderService{
    Orders createOrder(createOrderRequest req);
    Orders create(JsonNode orderData);
    List<Orders> findAll();
    List<Orders> findByUsername(String username);
}
