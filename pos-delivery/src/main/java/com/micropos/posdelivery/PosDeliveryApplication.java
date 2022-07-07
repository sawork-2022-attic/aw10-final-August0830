package com.micropos.posdelivery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
// @EnableDiscoveryClient
public class PosDeliveryApplication {

	public static void main(String[] args) {
		SpringApplication.run(PosDeliveryApplication.class, args);
		// DeliveryWebClient client = new DeliveryWebClient();
		// client.consume();
	}

}
