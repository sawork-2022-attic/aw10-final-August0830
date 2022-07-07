package com.micropos.posdelivery.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.micropos.dto.OrderInfoDto;
import com.micropos.dto.DeliveryInfoDto;
import com.micropos.posdelivery.mapper.DeliveryInfoMapper;
import com.micropos.posdelivery.model.DeliveryInfo;
import com.micropos.posdelivery.service.DeliveryService;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("/delivery")
public class DeliveryController {

    @Autowired
    private DeliveryService service;

    @Autowired
    private DeliveryInfoMapper mapper;

    @RequestMapping("/{deliveryId}")
    private Mono<DeliveryInfo> searchDeliveryInfo(@PathVariable("deliveryId") String deliveryId){
        
        return service.findDeliveryInfo(deliveryId);
    }

    @GetMapping(produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    private Flux<DeliveryInfo> getAllDeliveryInfo(){
        return service.allDeliveryInfo()
        // .doOnNext(deliveryInfo -> log.info(deliveryInfo.toString()))
        ;
    }

    @PostMapping("/add")
    private Mono<DeliveryInfoDto> addDeliveryInfo(@RequestBody String orderId){
        DeliveryInfo deliveryInfo = new DeliveryInfo(orderId, String.format("%8d",Math.abs(orderId.hashCode())), false);
        service.addDeliveryInfo(deliveryInfo);
        return Mono.just(mapper.toDeliveryInfoDto(deliveryInfo));
    }

}
