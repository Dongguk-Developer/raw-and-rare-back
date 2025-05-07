package com.example.raw_and_rare;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class RawAndRareApplication {

	public static void main(String[] args) {
		SpringApplication.run(RawAndRareApplication.class, args);
	}

}
