package com.nequi.franchises.manage_franchises;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Franchise API", version = "v1"))
public class ManageFranchisesApplication {

	public static void main(String[] args) {
		SpringApplication.run(ManageFranchisesApplication.class, args);
	}

}
