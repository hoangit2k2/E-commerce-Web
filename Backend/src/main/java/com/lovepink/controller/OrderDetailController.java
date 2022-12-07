package com.lovepink.controller;

import com.lovepink.entity.OrderDetails;
import com.lovepink.exception.NotFoundException;
import com.lovepink.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import com.lovepink.model.request.createOrderDetailsRequest;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
}
