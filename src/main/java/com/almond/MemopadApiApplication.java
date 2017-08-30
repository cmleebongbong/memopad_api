package com.almond;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration
public class MemopadApiApplication {
	public static void main(String[] args) {
		SpringApplication.run(MemopadApiApplication.class, args);
	}
}
