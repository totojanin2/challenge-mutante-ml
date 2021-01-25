package com.example.challengemutanteml;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@SpringBootApplication
@EnableAsync
public class ChallengeMutanteMlApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChallengeMutanteMlApplication.class, args);
	}

	@Bean
	public Executor taskExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();

		executor.setCorePoolSize(2);
		executor.setMaxPoolSize(6);
		executor.setQueueCapacity(1000000);
		executor.setThreadNamePrefix("apiRequestThread-");
		executor.initialize();

		return executor;
	}
}
