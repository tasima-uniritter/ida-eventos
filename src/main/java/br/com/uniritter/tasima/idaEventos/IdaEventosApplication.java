package br.com.uniritter.tasima.idaEventos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@SpringBootApplication
@Controller
public class IdaEventosApplication {
	@RequestMapping("/")
	@ResponseBody
	public String index(){
		return "funciona?";
	}
	public static void main(String[] args) {
		SpringApplication.run(IdaEventosApplication.class, args);
	}
}
