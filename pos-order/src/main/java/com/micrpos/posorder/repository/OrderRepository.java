package com.micrpos.posorder.repository;

import com.micrpos.posorder.model.OrderInfo;

import java.util.List;

public interface OrderRepository {
    OrderInfo addOrderInfo(String orderId, OrderInfo oinfo);

    List<OrderInfo> allOrderInfo();

    OrderInfo findOrderInfo(String orderId);
}
