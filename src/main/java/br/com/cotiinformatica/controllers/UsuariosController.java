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

import br.com.cotiinformatica.dtos.UsuarioPostDTO;
import br.com.cotiinformatica.entities.Usuario;
import br.com.cotiinformatica.exceptions.BadRequestException;
import br.com.cotiinformatica.exceptions.ServiceException;
import br.com.cotiinformatica.repositories.UsuarioRepository;
import br.com.cotiinformatica.security.Criptografia;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Transactional
@RequestMapping(value = "/api/usuarios")
@Api(tags = "Menu Usuários")
public class UsuariosController {

	@Autowired
	private UsuarioRepository usuarioRepository;

	// Método para cadastro de usuário
	@CrossOrigin
	@PostMapping
	@ApiOperation(value = "cadastrar")
	public ResponseEntity<String> post(@Valid @RequestBody UsuarioPostDTO dto) {

		try {

			// verificar se o email já está cadastrado no banco
			if (usuarioRepository.findByEmail(dto.getEmail()) != null) {
				throw new BadRequestException("O email informado já encontra-se cadastrado, por favor tente outro."); // HTTP 400
			}
			// caso false: realizar o cadastro
			Usuario usuario = new Usuario();
			usuario.setNome(dto.getNome());
			usuario.setEmail(dto.getEmail());
			usuario.setSenha(Criptografia.criptografar(dto.getSenha()));

			// gravando os dados usuário no banco, já com a senha criptografada
			usuarioRepository.save(usuario);

			// gerando mensagem de sucesso
			return ResponseEntity.status(HttpStatus.CREATED)
					.body("Usuário " + usuario.getNome() + " cadastrado com sucesso!"); // HTTP 201
		} catch (ServiceException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro: " + e.getMessage()); // HTTP 500
		}
	}

}