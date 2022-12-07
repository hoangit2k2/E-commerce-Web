package com.lovepink.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.lovepink.entity.Orders;
import com.lovepink.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.lovepink.service.OrderService;
import com.lovepink.model.request.createOrderRequest;
@CrossOrigin("*")
@RestController
public class OrderController {
    @Autowired
    OrderService orderService;
//    @PostMapping("/rest/order")
//    public ResponseEntity<?> createOrder(@RequestBody createOrderRequest req){
//        try {
//            Orders result =  orderService.createOrder(req);
//            System.out.println(result);
//            return ResponseEntity.ok(result);
//        }catch (Exception e){
//
//            throw new NotFoundException("Tạo Thất Bại");
//        }
//    }
    @PostMapping("/rest/order")
    public ResponseEntity<?> create(@RequestBody JsonNode orderData){
        try{
            Orders result = orderService.create(orderData);
            return ResponseEntity.ok(result);

        }catch (Exception e){
            System.out.println(e);
            throw new NotFoundException("Tao That Bai");
        }

    }
}
