package com.lovepink.service;

import com.lovepink.entity.OrderDetails;
import com.lovepink.service.lmpl.OrderDetailtServicelmpl;
import com.lovepink.model.request.createOrderDetailsRequest;
public interface OrderDetailService  {
    OrderDetails createOrderDetails(createOrderDetailsRequest req);
}
