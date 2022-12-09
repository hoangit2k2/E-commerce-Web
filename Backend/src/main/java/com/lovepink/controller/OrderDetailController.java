package com.lovepink.controller;

import com.lovepink.entity.OrderDetails;
import com.lovepink.exception.NotFoundException;
import com.lovepink.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import com.lovepink.model.request.createOrderDetailsRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
public class OrderDetailController {
    @Autowired
    OrderDetailService orderDetailService;
    @PostMapping("/rest/orderdetail")
    public ResponseEntity<?> createOrder(@RequestBody createOrderDetailsRequest req){
        try {
            OrderDetails result =  orderDetailService.createOrderDetails(req);
            System.out.println(result);
            return ResponseEntity.ok(result);
        }catch (Exception e){
            System.out.println(e);
            throw new NotFoundException("Tao That Bai");
        }
    }
    @GetMapping("/rest/orderdetail")
    public ResponseEntity<?> findAll(){
        try {
            List<OrderDetails> result = orderDetailService.findAll();
            return ResponseEntity.ok(result);
        }catch (Exception e){
            throw  new NotFoundException("Loi");
        }
    }
    @GetMapping("/rest/orderdetail/{id}")
    public ResponseEntity<?>  findOrderDetail(@PathVariable Integer id ){
        try {
            List<OrderDetails> result = orderDetailService.findorderDetail(id);
            return ResponseEntity.ok(result);
        }catch (Exception e){
            throw new NotFoundException("Loi");
        }
    }
}
