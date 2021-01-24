package com.example.challengemutanteml;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationStartupAware;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ChallengeMutanteMlApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChallengeMutanteMlApplication.class, args);
	}

	@Bean
	ApplicationRunner applicationRunner(DNARepository dnaRepository) {
		return args -> {
			dnaRepository.save(new DNA(true, "AAAACTTTCTGATTATGTAGAAGGCCCCTATCACTG"));
			dnaRepository.save(new DNA(false, "AAAACTTTCTGATTATGTAGAAGGCGCCTATCACTG"));
			dnaRepository.save(new DNA(true, "TTTTCTTTCTGATTATGTAGAAGGCCCCTATCACTG"));
		};
	}
}
