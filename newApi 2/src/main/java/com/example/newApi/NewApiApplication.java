package com.example.newApi;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class NewApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(NewApiApplication.class, args);
	}

}
