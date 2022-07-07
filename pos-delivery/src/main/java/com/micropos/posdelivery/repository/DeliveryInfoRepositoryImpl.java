package com.micropos.posdelivery.repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Repository;

import com.micropos.posdelivery.model.DeliveryInfo;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class DeliveryInfoRepositoryImpl implements DeliveryInfoRepository{

    private static Map<String,DeliveryInfo> repository;

    static {
        repository =  new ConcurrentHashMap<String,DeliveryInfo>();
        // DeliveryInfo info = new DeliveryInfo("101", "100", false);
        // repository.put("100",info); 
    }

    @Override
    public Mono<DeliveryInfo> addDeliveryInfo(String deliveryInfoId,DeliveryInfo deliveryInfo) {    
        //return previous value if existed
        repository.put(deliveryInfoId, deliveryInfo);
        return Mono.just(deliveryInfo);
    }

    @Override
    public Flux<DeliveryInfo> allDeliveryInfo() {
        return Flux.fromIterable(repository.values());
    }

    @Override
    public Mono<DeliveryInfo> findDeliveryInfo(String deliveryInfoId) {
        DeliveryInfo dInfo = repository.getOrDefault(deliveryInfoId, null);
        if(dInfo == null)
            dInfo = new DeliveryInfo("not found", "not found", false);
        return Mono.just(dInfo);
    }
    
}
