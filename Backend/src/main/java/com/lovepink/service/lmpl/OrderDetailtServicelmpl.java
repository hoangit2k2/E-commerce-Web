package com.lovepink.service.lmpl;

import com.lovepink.Dao.OrderDetailDao;
import com.lovepink.entity.OrderDetails;
import com.lovepink.service.OrderDetailService;
import com.lovepink.model.request.createOrderDetailsRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderDetailtServicelmpl implements OrderDetailService {
    @Autowired
    OrderDetailDao dao;

    public OrderDetails createOrderDetails(createOrderDetailsRequest req){
        OrderDetails orderDetails = OrderDetails.toOrderDetail(req);
        dao.save(orderDetails);
        return  orderDetails;
    }
    public List<OrderDetails> findAll(){
        return dao.findAll();
    }
    public  List<OrderDetails> findorderDetail(Integer id){
        return dao.findByOrders_Id(id);
    }
}
