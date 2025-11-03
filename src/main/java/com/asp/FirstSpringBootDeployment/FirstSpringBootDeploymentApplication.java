package com.asp.FirstSpringBootDeployment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class FirstSpringBootDeploymentApplication {

	public static void main(String[] args) {
		SpringApplication.run(FirstSpringBootDeploymentApplication.class, args);
        System.out.println("Application Started Successfully");
	}

}
