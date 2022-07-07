package com.micrpos.posorder.repository;

import com.micrpos.posorder.model.OrderInfo;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class OrderRepositoryImpl implements OrderRepository{

    private static Map<String,OrderInfo> repository;

    static {
        repository = new ConcurrentHashMap<String,OrderInfo>();
    }

    @Override
    public OrderInfo addOrderInfo(String orderId, OrderInfo oinfo) {
        repository.put(orderId,oinfo);
        return oinfo;
    }

    @Override
    public List<OrderInfo> allOrderInfo() {
        List<OrderInfo> list = new ArrayList<>();
        for(OrderInfo oinfo:repository.values())
            list.add(oinfo);
        return list;
    }

    @Override
    public OrderInfo findOrderInfo(String orderId) {
        return repository.getOrDefault(orderId, null);
    }
    
}
