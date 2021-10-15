package com.where.WhereYouAt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@Configuration
@ServletComponentScan
public class WhereYouAtApplication {
	public static void main(String[] args) {
		SpringApplication.run(WhereYouAtApplication.class, args);
	}

}
