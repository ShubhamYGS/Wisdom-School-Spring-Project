package com.webapp.ygsschool;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/*
* No need to put @EntityScan and @EnableJpaRepositories if you are putting your main class out-side of any other package
*/

@SpringBootApplication
//@EntityScan("com.webapp.ygsschool.model")
//@EnableJpaRepositories("com.webapp.ygsschool.repository")
public class YgsschoolApplication {
	public static void main(String[] args) {
		SpringApplication.run(YgsschoolApplication.class, args);
	}

}
