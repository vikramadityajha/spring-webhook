package com.example.springwebhooks;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class SpringWebHooksApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringWebHooksApplication.class, args);
	}

	@Bean(name = "defaultRestTemplate")
	public RestTemplate getRestTemplate() {
		RestTemplate restTemplate = new RestTemplate();
		return restTemplate;
	}
}
