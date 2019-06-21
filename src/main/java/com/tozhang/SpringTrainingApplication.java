package com.tozhang;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@SpringBootApplication
@EnableJpaAuditing
@ComponentScan(basePackages = "com.tozhang")
@EnableAutoConfiguration
@PropertySources({
		@PropertySource("classpath:application.properties"),
		@PropertySource("classpath:auth0.properties")
})
public class SpringTrainingApplication extends SpringBootServletInitializer{

	@Autowired
	private ApplicationContext applicationContext;
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringTrainingApplication.class, args);
	}

//	@Override
//	public void run(String...args) throws Exception {
//		String[] beans = applicationContext.getBeanDefinitionNames();
//
//		for (String bean: beans) {
//			System.out.println(bean);
//		}
//	}
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(SpringTrainingApplication.class);
	}

}

//@SpringBootApplication
//public class SpringTrainingApplication extends SpringBootServletInitializer {
//
//	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
//		return application.sources(Application.class);
//	}
//
//	public static void main(String[] args) throws Exception {
//		SpringApplication.run(Application.class, args);
//	}
//
//}

