package com.joe.racthk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@ComponentScan(basePackages = "com.joe.racthk.rest;") // Replace with the package where your controllers are located
@ComponentScan(basePackages = "com.joe.racthk.service;") // Replace with the package where your controllers are located
public class RacthkApplication {

	public static void main(String[] args) {
		SpringApplication.run(RacthkApplication.class, args);
	}

}
