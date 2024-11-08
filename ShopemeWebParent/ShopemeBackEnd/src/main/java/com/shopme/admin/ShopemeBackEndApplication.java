package com.shopme.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan({"com.shopme.commom.entity","com.shopme.admin.repository"})
public class ShopemeBackEndApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShopemeBackEndApplication.class, args);
	}

}
