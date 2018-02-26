package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class DemoApplication {

	private static final Logger logger = LoggerFactory.getLogger(DemoApplication.class);
	
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

		
		@Bean
		public CommandLineRunner setup(ContactRepository contactRepository) {
			return (args) -> {
				contactRepository.save(new Contact("William", "Smith", "williams123@gmail.com", "3761520879"));
				contactRepository.save(new Contact("Daniel", "Moore", "daniel.moore@gmail.com", "2738098078"));
				contactRepository.save(new Contact("David", "Harris", "davidharris90@gmail.com", "4920173829"));
				contactRepository.save(new Contact("Linda", "Nelson", "linda2004@gmail.com", "2910287352"));
				contactRepository.save(new Contact("Sarah", "Parker", "sarah.parker12@gmail.com", "4891092648"));
				logger.info("The sample data has been generated");
			};
		}
}