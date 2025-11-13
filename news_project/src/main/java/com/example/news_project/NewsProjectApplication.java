package com.example.news_project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class NewsProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(NewsProjectApplication.class, args);
	}

}
