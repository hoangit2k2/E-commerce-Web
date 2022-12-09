package com.lovepink.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.lovepink.entity.Orders;
import com.lovepink.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.lovepink.service.OrderService;
import com.lovepink.model.request.createOrderRequest;

import java.util.List;

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
    @GetMapping("/rest/order")
    public  ResponseEntity<?> getOrder(){
        try {
            List<Orders> result = orderService.findAll();
            return ResponseEntity.ok(result);
        }catch (Exception e){
            throw new NotFoundException("Tao That Bai");
        }
    }
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
    @GetMapping("/rest/order/{username}")
    public ResponseEntity<?> findByUsername(@PathVariable String username){
        try {
            List<Orders> result = orderService.findByUsername(username);
            return ResponseEntity.ok(result);
        }catch (Exception e){
            throw new NotFoundException("tao That Bai");

        }
    }
}
