package com.lovepink.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.stereotype.Component;
import com.lovepink.model.request.createOrderDetailsRequest;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Data
@Table
@NoArgsConstructor
@AllArgsConstructor
@Component
@Entity(name = "ORDERDETAILS")
public class OrderDetails {
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Id
    int ID;
    float price;
    int quantity;
    @ManyToOne
    @JoinColumn(name = "contentid")
    Content content;
    @JsonIgnore @ManyToOne
    @JoinColumn(name = "orderid")
    Orders orders;
    public static OrderDetails toOrderDetail(createOrderDetailsRequest req){
        OrderDetails orderDetails = new OrderDetails();
//        orderDetails.setOrderid(req.getOrderid());
//        orderDetails.setContentid(req.getContentid());
        orderDetails.setPrice(req.getPrice());
        orderDetails.setQuantity(req.getQuantity());
        return orderDetails;
    }
}
