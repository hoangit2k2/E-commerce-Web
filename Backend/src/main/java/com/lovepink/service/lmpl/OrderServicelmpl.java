package com.lovepink.service.lmpl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lovepink.Dao.OrderDao;
import com.lovepink.Dao.OrderDetailDao;
import com.lovepink.entity.OrderDetails;
import com.lovepink.entity.Orders;
import com.lovepink.service.OrderService;
import com.lovepink.model.request.createOrderRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServicelmpl implements OrderService {
    @Autowired
    OrderDao dao;
//    @Autowired
//    OrderDetailDao dao
    @Autowired
    OrderDetailDao detailDao;

    @Override
    public Orders createOrder(createOrderRequest req){
        Orders orders = Orders.toOrder(req);
        dao.save(orders);
        return orders;
    }
    @Override
    public Orders create(JsonNode orderData){
        ObjectMapper mapper = new ObjectMapper();
        Orders orders = mapper.convertValue(orderData, Orders.class);
        TypeReference<List<OrderDetails>> type = new TypeReference<List<OrderDetails>> (){};
        List<OrderDetails> details = mapper.convertValue(orderData.get("orderDetails"),type)
                .stream().peek(d -> d.setOrders(orders))
                .collect(Collectors.toList());
        dao.save(orders);
        detailDao.saveAll(details);
        return  orders;
    }
    @Override
    public List<Orders> findAll(){
        return dao.findAll();
    }
    @Override
    public  List<Orders> findByUsername(String username){
        return  dao.findByUsernameid(username);
    }
}
