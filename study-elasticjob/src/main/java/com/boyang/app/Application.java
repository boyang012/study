package com.boyang.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication 
@ComponentScan(basePackages = {"com.boyang.*"})
public class Application {

	public static void main(String[] args) {
		System.out.println("hlllll");
		SpringApplication.run(Application.class, args);
	}
}
