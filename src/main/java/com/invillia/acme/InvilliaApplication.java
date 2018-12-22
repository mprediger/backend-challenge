package com.invillia.acme;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
//@EntityScan(basePackages = { "com.invillia.model" })
//@EnableJpaRepositories(basePackages = { "com.invillia.repository" })
//@ComponentScan(basePackages = {"com.invillia.controller"})
@EntityScan
@EnableJpaRepositories
@ComponentScan
public class InvilliaApplication {

	public static void main(String[] args) {
		SpringApplication.run(InvilliaApplication.class, args);
	}
	
}
