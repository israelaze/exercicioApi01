//Classe que contém o atributo(campo) que servirá como parâmetro para 
//o serviço da API (RecuperSenhaController)

package br.com.cotiinformatica.dtos;

public class RecuperarSenhaPostDTO {
	
	//neste caso, o email é o único campo necessário
	private String email; 

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	
	

}
