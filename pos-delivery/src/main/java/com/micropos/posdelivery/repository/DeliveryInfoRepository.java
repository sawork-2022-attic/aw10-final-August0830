package com.micropos.posdelivery.repository;


import com.micropos.posdelivery.model.DeliveryInfo;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface DeliveryInfoRepository{
    Mono<DeliveryInfo> addDeliveryInfo(String deliveryInfoId,DeliveryInfo deliveryInfo);
    Flux<DeliveryInfo> allDeliveryInfo();
    Mono<DeliveryInfo> findDeliveryInfo(String deliveryInfoId);
}