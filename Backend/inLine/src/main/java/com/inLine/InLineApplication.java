package com.inLine;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = "com.inLine.dao")
@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "inLine API", version = "1.0.0", description = "Crowd control application"))
public class InLineApplication {

	public static void main(String[] args) {
		SpringApplication.run(InLineApplication.class, args);
	}

}
