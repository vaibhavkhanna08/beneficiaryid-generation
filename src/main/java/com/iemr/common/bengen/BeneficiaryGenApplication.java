package com.iemr.common.bengen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableScheduling
public class BeneficiaryGenApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(beneficiaryGenApplication, args);
	}

	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(new Class[] { BeneficiaryGenApplication.class });
	}

	private static Class<BeneficiaryGenApplication> beneficiaryGenApplication = BeneficiaryGenApplication.class;

	@Bean
	RestTemplate restTemplate() {
		return new RestTemplate();
	}
	
}

