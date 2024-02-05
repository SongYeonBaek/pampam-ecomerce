package com.example.demo.member;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class SellerApplication {
	public static void main(String[] args) {
		SpringApplication.run(SellerApplication.class, args);
	}
}






