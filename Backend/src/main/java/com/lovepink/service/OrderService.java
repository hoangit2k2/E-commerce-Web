package com.lovepink.service;
import com.fasterxml.jackson.databind.JsonNode;
import com.lovepink.entity.Orders;
import com.lovepink.model.request.createOrderRequest;


public interface OrderService {
    Orders createOrder(createOrderRequest req);
    Orders create(JsonNode orderData);
}
