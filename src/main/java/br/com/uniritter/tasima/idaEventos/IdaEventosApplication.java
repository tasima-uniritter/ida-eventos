package br.com.uniritter.tasima.idaEventos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@ComponentScan
@SpringBootApplication
public class IdaEventosApplication {

	public static void main(String[] args) {
		SpringApplication.run(IdaEventosApplication.class, args);
	}
}
