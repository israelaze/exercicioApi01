//Classe Principal

package br.com.cotiinformatica;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "br.com.cotiinformatica")
public class ExercicioSpringApi01Application {

	public static void main(String[] args) {
		SpringApplication.run(ExercicioSpringApi01Application.class, args);
	}

}
