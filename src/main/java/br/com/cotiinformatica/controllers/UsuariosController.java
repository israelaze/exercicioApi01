package br.com.cotiinformatica.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.cotiinformatica.dtos.UsuarioGetDTO;
import br.com.cotiinformatica.dtos.UsuarioPostDTO;
import br.com.cotiinformatica.entities.Usuario;
import br.com.cotiinformatica.interfaces.IUsuarioRepository;
import br.com.cotiinformatica.security.Criptografia;
import br.com.cotiinformatica.security.TokenSecurity;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

@Controller
@Transactional
public class UsuariosController {

	private static final String ENDPOINT= "/api/usuarios";
	
	@Autowired
	private IUsuarioRepository usuarioRepository;
	
	//Método para cadastro de usuário
	@CrossOrigin
	@RequestMapping(value = ENDPOINT, method = RequestMethod.POST)
	@RequestBody
	public ResponseEntity<String>post(@RequestBody UsuarioPostDTO dto){
		
		try {
			
			//verificar se o email já está cadastrado no banco
			if(usuarioRepository.findByEmail(dto.getEmail()) != null) {
			//caso true: erro
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body("O email informado já encontra-se cadastrado, por favor tente outro."); //HTTP 400
			}
			//caso false: realizar o cadastro
				Usuario usuario = new Usuario();
				usuario.setNome(dto.getNome());
				usuario.setEmail(dto.getEmail());
				usuario.setSenha(Criptografia.criptografar(dto.getSenha())); //criptografa a senha informada 
				
				//gravando os dados usuário no banco, já com a senha criptografada
				usuarioRepository.save(usuario);
				
				//gerando mensagem de sucesso
				return ResponseEntity.status(HttpStatus.OK)
						.body("Usuário " + usuario.getNome() + " cadastrado com sucesso!"); //HTTP 200
		}
		catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Erro: " + e.getMessage());  //HTTP 500
		}
	}
	
	//Método para consultar todos os Usuários
		@CrossOrigin
		@RequestMapping(value = ENDPOINT, method = RequestMethod.GET)
		@RequestBody
		public ResponseEntity<List<UsuarioGetDTO>>get(){ // não retorna String e sim dados em forma de lista
			
			try {
				//declarando uma lista da classe ClienteGetDTO
				List<UsuarioGetDTO> result = new ArrayList<UsuarioGetDTO>();
				
				//consultar e percorrer os clientes obtidos no banco de dados..
				for(Usuario usuario : usuarioRepository.findAll()) {
					
				//transferindo os dados do cliente pro objeto dto
					UsuarioGetDTO dto = new UsuarioGetDTO();
					
					dto.setIdUsuario(usuario.getIdUsuario());
					dto.setNome(usuario.getNome());
					dto.setEmail(usuario.getEmail());
					dto.setAccessToken(TokenSecurity.generateToken(usuario.getEmail()));
					
					//adicionar os clientes do objeto dto na lista, através do objeto result
					result.add(dto);
					
				}
				
				//retornando a lista, sem nenhuma mensagem de retorno
				return ResponseEntity.status(HttpStatus.OK).body(result);
				
			}
			catch(Exception e) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
						.body(null); //HTTP 500
				
			}
				
		}
}