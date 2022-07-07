package com.micropos.posdelivery;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.springframework.web.reactive.function.client.WebClient;

import com.micropos.posdelivery.model.DeliveryInfo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DeliveryWebClient {
    public static final org.slf4j.Logger log = LoggerFactory.getLogger(DeliveryWebClient.class);
    WebClient client = WebClient.create("http://localhost:8090");
    public void consume(){

        client.get().uri("/delivery/{deliveryId}","100").retrieve()
        .bodyToFlux(DeliveryInfo.class)
        // .map(this::generateDelivery)
        .subscribe(new Subscriber<DeliveryInfo>() {
            // private Subscription subscription;

            @Override
            public void onComplete() {
                log.info("Delivery service completed.");
            }

            @Override
            public void onError(Throwable arg0) {
                // TODO Auto-generated method stub
                
            }

            @Override
            public void onNext(DeliveryInfo arg0) {
                // TODO Auto-generated method stub
                
            }

            @Override
            public void onSubscribe(Subscription subscription) {
                // this.subscription = subscription;
                subscription.request(1);
                log.info("A delivery starts!");
            }

        });
        
    }

    private DeliveryInfo generateDelivery(DeliveryInfo deliveryInfo){
        deliveryInfo.setDeliveryId("00x");
        //todo sleep random time
        log.info("Delivery takes time.");
        return deliveryInfo;
    }
}
