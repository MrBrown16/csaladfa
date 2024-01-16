package hu.morabarna.csaladfa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@SpringBootApplication
@EnableJpaRepositories(basePackages = "hu.morabarna.csaladfa")
public class CsaladfaApplication {

	public static void main(String[] args) {
		// SpringApplication.run(CsaladfaApplication.class, args);
		SpringApplication app = new SpringApplication(CsaladfaApplication.class);
		app.setWebApplicationType(WebApplicationType.SERVLET);
		
		app.run(args);
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(@NonNull CorsRegistry registry) {
				registry.addMapping("/**").allowedMethods("GET","HEAD","POST","PUT","DELETE").allowedOrigins("http://localhost:4200");
			}
		};
	}
}
