package com.micropos.posdelivery.service;


import java.time.Duration;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.micropos.posdelivery.model.DeliveryInfo;
import com.micropos.posdelivery.repository.DeliveryInfoRepository;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class DeliveryServiceImpl implements DeliveryService{

    private Random rand = new Random(10);

    @Autowired
    private DeliveryInfoRepository repos;

    @Override
    public Mono<DeliveryInfo> addDeliveryInfo(DeliveryInfo deliveryInfo) {
        log.info("add service");
        return repos.addDeliveryInfo(deliveryInfo.getDeliveryId(), deliveryInfo)
        .delayElement(Duration.ofMillis(rand.nextInt(10000)))
        .doOnNext(info -> {
            info.setDelivered(true);
            log.info("Delivery takes time.");
        });
        // return true;
    }

    @Override
    public Mono<DeliveryInfo> findDeliveryInfo(String deliveryId) {
        return repos.findDeliveryInfo(deliveryId);
    }

    @Override
    public Flux<DeliveryInfo> allDeliveryInfo() {
        return repos.allDeliveryInfo();
    }
    
}
