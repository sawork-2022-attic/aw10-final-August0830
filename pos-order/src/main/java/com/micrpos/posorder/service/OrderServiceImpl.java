package com.micrpos.posorder.service;

import java.util.List;
import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.Flow.Subscription;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.micropos.dto.CartItemDto;
import com.micropos.dto.DeliveryInfoDto;
import com.micropos.dto.OrderInfoDto;
import com.micrpos.posorder.mapper.OrderInfoMapper;
import com.micrpos.posorder.model.OrderInfo;
import com.micrpos.posorder.repository.OrderRepository;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class OrderServiceImpl implements OrderService{

    WebClient client = WebClient.create("http://localhost:8090");

    @Autowired
    private OrderRepository repos;

    @Autowired
    private OrderInfoMapper mapper;

    @Autowired
    private StreamBridge streamBridge;
    @Override
    public Mono<OrderInfoDto> addOrderInfo(OrderInfo info) {
        repos.addOrderInfo(info.getOrderId(), info);
        //deliveryOrderByMsg(info);
        return Mono.just(mapper.toOrderInfoDto(repos.addOrderInfo(info.getOrderId(), info)));
    }


    @Override
    public Mono<OrderInfoDto> findOrderInfo(String orderId) {
        return Mono.just(mapper.toOrderInfoDto(repos.findOrderInfo(orderId)));
    }

    @Override
    public Flux<OrderInfoDto> allOrderInfo() {
        List<OrderInfoDto> odtos = new ArrayList<>();
        for(OrderInfo oinfo:repos.allOrderInfo())
            odtos.add(mapper.toOrderInfoDto(oinfo));
        return Flux.fromIterable(odtos);
    }

    private void deliveryOrderByMsg(OrderInfo oinfo) {
        String deliveryId = String.format("D%8d", oinfo.hashCode());
        DeliveryInfoDto dInfoDto = new DeliveryInfoDto();
        dInfoDto.setDeliveryId(deliveryId);
        dInfoDto.setIsDelivered(false);
        dInfoDto.setOrderId(oinfo.getOrderId());
        streamBridge.send("order-out", dInfoDto);
        //streamBridge send to delivery
    }
   
}
