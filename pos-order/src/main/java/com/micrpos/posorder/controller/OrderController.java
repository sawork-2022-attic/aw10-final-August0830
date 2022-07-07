package com.micrpos.posorder.controller;

import java.time.Duration;
import java.util.List;
import java.util.Random;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.http.MediaType;

import com.micropos.dto.CartDto;
import com.micropos.dto.CartItemDto;
import com.micropos.dto.DeliveryInfoDto;
import com.micropos.dto.OrderInfoDto;
import com.micrpos.posorder.mapper.OrderInfoMapper;
import com.micrpos.posorder.model.OrderInfo;
import com.micrpos.posorder.service.OrderService;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/order")
@Slf4j
public class OrderController {
    private Random rand = new Random();
    @Autowired
    private OrderService orderService;

    WebClient client = WebClient.create("http://localhost:8090/delivery");

    @Autowired
    private OrderInfoMapper mapper;

    @GetMapping("/{orderId}")
    private Mono<OrderInfoDto> searchOrderInfo(@PathVariable String orderId){
        return orderService.findOrderInfo(orderId);
    }

    @GetMapping(produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    private Flux<OrderInfoDto> allOrderInfo(){
        return orderService.allOrderInfo();
    }

    @PostMapping("/add")
    private Mono<OrderInfoDto> addOrderInfo(@RequestBody CartDto cartDto){
        List<CartItemDto> items = cartDto.getItems();
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setItems(items);
        orderInfo.setOrderId(String.format("o%8d", cartDto.hashCode()));
        orderService.addOrderInfo(orderInfo);
        // log.info(orderInfo.toString());
        client.post().uri("/add")
        .header("Content-type", "application/json")
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        .bodyValue(orderInfo.getOrderId())
        .retrieve().bodyToMono(DeliveryInfoDto.class)
        .delayElement(Duration.ofMillis(rand.nextInt(100)))
        .subscribe(
            new Subscriber<DeliveryInfoDto>() {
                // private Subscription subscription;

                @Override
                public void onComplete() {
                    // TODO Auto-generated method stub

                    log.info("order was processed and triggered delivery");
                }

                @Override
                public void onError(Throwable arg0) {
                    // TODO Auto-generated method stub
                    
                }

                @Override
                public void onNext(DeliveryInfoDto deliveryInfoDto) {
                    // TODO Auto-generated method stub
                    // log.info(deliveryInfoDto.toString());
                }

                @Override
                public void onSubscribe(Subscription subscription) {
                    // TODO Auto-generated method stub
                    // this.subscription = subscription;
                    subscription.request(1);
                    log.info("A order starts!");
                }

            }
        );
        return Mono.just(mapper.toOrderInfoDto(orderInfo));
    }
    
}
