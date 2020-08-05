package com.inLine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = "com.inLine.dao")
@SpringBootApplication
public class InLineApplication {

	public static void main(String[] args) {
		SpringApplication.run(InLineApplication.class, args);
	}

}
