package hu.morabarna.csaladfa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "hu.morabarna.csaladfa")
public class CsaladfaApplication {

	public static void main(String[] args) {
		// SpringApplication.run(CsaladfaApplication.class, args);
		SpringApplication app = new SpringApplication(CsaladfaApplication.class);
		app.setWebApplicationType(WebApplicationType.SERVLET);
		app.run(args);
	}

}
