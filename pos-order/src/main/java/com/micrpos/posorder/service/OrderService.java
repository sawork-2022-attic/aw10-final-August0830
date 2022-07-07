package com.micrpos.posorder.service;

import com.micropos.dto.CartItemDto;
import com.micropos.dto.OrderInfoDto;
import com.micrpos.posorder.model.OrderInfo;

import java.util.List;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface OrderService {
    Mono<OrderInfoDto> addOrderInfo(OrderInfo info);
    Mono<OrderInfoDto> findOrderInfo(String orderId);
    Flux<OrderInfoDto> allOrderInfo();
}
