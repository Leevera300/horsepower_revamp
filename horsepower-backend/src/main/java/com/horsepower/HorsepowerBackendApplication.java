package com.horsepower;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing // 이거 꼭 있어야 자동 설정 됨!
public class HorsepowerBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(HorsepowerBackendApplication.class, args);
	}

}
