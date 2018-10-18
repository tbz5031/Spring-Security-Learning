package com.tozhang;


import com.tozhang.training.data.service.GuestService;
import com.tozhang.training.util.Output;
import com.tozhang.training.util.ServiceRuntimeException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootApplication
@EnableJpaAuditing
public class SpringTrainingApplication {

	public static void main(String[] args) {
			SpringApplication.run(SpringTrainingApplication.class, args);
	}
}
