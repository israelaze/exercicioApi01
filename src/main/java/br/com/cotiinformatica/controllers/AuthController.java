package br.com.cotiinformatica.controllers;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.cotiinformatica.dtos.AuthPostDTO;
import br.com.cotiinformatica.dtos.UsuarioGetDTO;
import br.com.cotiinformatica.entities.Usuario;
import br.com.cotiinformatica.exceptions.BadRequestException;
import br.com.cotiinformatica.exceptions.ServiceException;
import br.com.cotiinformatica.repositories.UsuarioRepository;
import br.com.cotiinformatica.security.Criptografia;
import br.com.cotiinformatica.security.TokenSecurity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Transactional
@RequestMapping(value = "/api/auth")
@Api(tags = "Autenticação")
public class AuthController {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	//serviço para autenticação
	@CrossOrigin
	@PostMapping
	@ApiOperation(value = "Login")
	public ResponseEntity<UsuarioGetDTO> post(@Valid @RequestBody AuthPostDTO dto){
		
		try {
			//Procurar o usuário no banco através do email e senha.Criptografar senha
			Usuario usuario = usuarioRepository.findByEmailAndSenha(dto.getEmail(),Criptografia.criptografar(dto.getSenha()));
			
			//Verificar se o usuário foi encontrado
			if(usuario != null) {
				
				//Objeto para retornar os dados
				UsuarioGetDTO result = new UsuarioGetDTO();
				
				result.setIdUsuario(usuario.getIdUsuario());
				result.setNome(usuario.getNome());
				result.setEmail(usuario.getEmail());
				result.setAccessToken(TokenSecurity.generateToken(usuario.getEmail()));
				
				//retorna um UsuarioGetDTO(result)
				return ResponseEntity.status(HttpStatus.OK)
						.body(result); //HTTP 200
			}
			else {
				
				throw new BadRequestException("Email ou senha inválidos!"); // HTTP 400
				
			}
		}
		catch(ServiceException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(null); //HTTP 500
			
		}
	
	}
}
