package com.micropos.posdelivery.service;


import com.micropos.posdelivery.model.DeliveryInfo;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface DeliveryService {
    Mono<DeliveryInfo> addDeliveryInfo(DeliveryInfo deliveryInfo);//for test
    Mono<DeliveryInfo> findDeliveryInfo(String deliveryId);
    Flux<DeliveryInfo> allDeliveryInfo();
}
