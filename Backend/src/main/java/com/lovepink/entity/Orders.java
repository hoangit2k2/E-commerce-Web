package com.lovepink.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

import com.lovepink.model.request.createOrderRequest;
@Data
@Table
@NoArgsConstructor
@AllArgsConstructor
@Component
@Entity(name = "ORDERS")
public class Orders {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    Integer id;
    String usernameid;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    Date datetime = new Date();
    String address;
    @OneToMany(mappedBy = "orders", cascade = CascadeType.ALL)
    List<OrderDetails> orderDetails;
    String note;
    boolean pay;
    public static Orders toOrder(createOrderRequest req){
        Orders orders = new Orders();
//        orders.setDatetime(java.time.LocalDateTime.now());
        orders.setUsernameid(req.getUsernameid());
        orders.setAddress(req.getAddress());
        return orders;
    }
	public Orders(Integer id) {
		this.id = id;
	}
    
	
}
