package br.com.cotiinformatica.dtos;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class UsuarioPostDTO {

	@NotBlank(message =  "{nome.not.blank}")
	private String nome;
	
	@NotBlank(message = "{email.not.blank}")
	@Email(message = "{email.email}")
	private String email;
	
	@NotBlank(message = "{senha.not.blank}")
	private String senha;
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	
	
}
