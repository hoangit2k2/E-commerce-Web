package com.lovepink.service;

import com.lovepink.entity.OrderDetails;
import com.lovepink.service.lmpl.OrderDetailtServicelmpl;
import com.lovepink.model.request.createOrderDetailsRequest;

import java.util.List;

public interface OrderDetailService  {
    OrderDetails createOrderDetails(createOrderDetailsRequest req);
    List<OrderDetails> findAll();
    List<OrderDetails> findorderDetail(Integer id);
}
