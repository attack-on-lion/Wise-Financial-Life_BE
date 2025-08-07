package com.wisespendinglife.wise_spending_life;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class WiseSpendingLifeApplication {

	public static void main(String[] args) {
		SpringApplication.run(WiseSpendingLifeApplication.class, args);
	}

}
