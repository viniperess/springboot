package br.edu.ifsul.cstsi.tads_curso;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class TadsCursoApplication {

	public static void main(String[] args) {
		SpringApplication.run(TadsCursoApplication.class, args);
	}

	@GetMapping("/inicio")
	public String inicio(){
		return "Ola spring boot";
	}

}
