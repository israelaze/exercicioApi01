package br.com.cotiinformatica.dtos;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class RecuperarSenhaPostDTO {
	
	@NotBlank(message = "{email.not.blank}")
	@Email(message = "{email.email}")
	private String email; 

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
}
