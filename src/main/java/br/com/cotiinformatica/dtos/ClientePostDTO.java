package br.com.cotiinformatica.dtos;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class ClientePostDTO{
	
	@NotBlank(message =  "{nome.not.blank}")
	private String nome;
	
	@NotBlank(message =  "{cpf.not.blank}")
	private String cpf;
	
	@NotBlank(message =  "{email.not.blank}")
	@Email
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
