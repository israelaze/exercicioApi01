package br.com.cotiinformatica.dtos;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class ClientePostDTO {

	@NotBlank(message = "{nome.not.blank}")
	private String nome;

	@NotBlank(message = "{cpf.not.blank}")
<<<<<<< HEAD
	@Size(min = 11, max = 15, message = "{cpf.size}")
=======
	@Size(max = 15, message = "{cpf.size}")
>>>>>>> 411e16d64d240afed864f402495bcfec7c2de83f
	private String cpf;

	@NotBlank(message = "{email.not.blank}")
	@Email(message = "{email.email}")
	private String email;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
