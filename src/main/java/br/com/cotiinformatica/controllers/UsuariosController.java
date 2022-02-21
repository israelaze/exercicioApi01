package br.com.cotiinformatica.controllers;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.cotiinformatica.dtos.UsuarioPostDTO;
import br.com.cotiinformatica.entities.Usuario;
import br.com.cotiinformatica.interfaces.IUsuarioRepository;
import br.com.cotiinformatica.security.Criptografia;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@Transactional
public class UsuariosController {

	private static final String ENDPOINT = "/api/usuarios";

	@Autowired
	private IUsuarioRepository usuarioRepository;

	// Método para cadastro de usuário
	@CrossOrigin
	@RequestMapping(value = ENDPOINT, method = RequestMethod.POST)
	public ResponseEntity<String> post(@RequestBody UsuarioPostDTO dto) {

		try {

			// verificar se o email já está cadastrado no banco
			if (usuarioRepository.findByEmail(dto.getEmail()) != null) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body("O email informado já encontra-se cadastrado, por favor tente outro."); // HTTP 400
			}
			// caso false: realizar o cadastro
			Usuario usuario = new Usuario();
			usuario.setNome("Israel");
			usuario.setEmail("isra@gmail.com");
			usuario.setSenha(Criptografia.criptografar("1234"));

			// gravando os dados usuário no banco, já com a senha criptografada
			usuarioRepository.save(usuario);

			// gerando mensagem de sucesso
			return ResponseEntity.status(HttpStatus.CREATED)
					.body("Usuário " + usuario.getNome() + " cadastrado com sucesso!"); // HTTP 201
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro: " + e.getMessage()); // HTTP 500
		}
	}

}