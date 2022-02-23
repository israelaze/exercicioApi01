package br.com.cotiinformatica.services;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import br.com.cotiinformatica.dtos.UsuarioPostDTO;
import br.com.cotiinformatica.entities.Usuario;
import br.com.cotiinformatica.exceptions.BadRequestException;
import br.com.cotiinformatica.repositories.UsuarioRepository;
import br.com.cotiinformatica.security.Criptografia;
import lombok.AllArgsConstructor;

@Service
@Transactional
@AllArgsConstructor
public class UsuarioService {

	private final UsuarioRepository repository;

	public String cadastrar(UsuarioPostDTO dto) {

		// verificar se o email já está cadastrado no banco
		if (repository.findByEmail(dto.getEmail()) != null) {
			throw new BadRequestException("O email informado já encontra-se cadastrado, por favor tente outro.");
		}

		// inserindo os dados do Usuário e criptografando sua senha
		Usuario usuario = new Usuario();
		usuario.setNome(dto.getNome());
		usuario.setEmail(dto.getEmail());
		usuario.setSenha(Criptografia.criptografar(dto.getSenha()));

		repository.save(usuario);

		String response = "Usuário " + usuario.getNome() + " cadastrado com sucesso.";
		return response;

	}
}
