package br.com.cotiinformatica.services;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import br.com.cotiinformatica.dtos.RecuperarSenhaPostDTO;
import br.com.cotiinformatica.entities.Usuario;
import br.com.cotiinformatica.exceptions.EntityNotFoundException;
import br.com.cotiinformatica.exceptions.ServiceException;
import br.com.cotiinformatica.helpers.PasswordHelper;
import br.com.cotiinformatica.repositories.UsuarioRepository;
import br.com.cotiinformatica.security.Criptografia;
import br.com.cotiinformatica.senders.MailSender;
import lombok.AllArgsConstructor;

@Service
@Transactional
@AllArgsConstructor
public class RecuperarSenhaService {

	private UsuarioRepository repository;
	private final MailSender mailSender;

	public String recuperarSenha(RecuperarSenhaPostDTO dto) {

		// consultando o usuario no banco de dados atraves do email
		Usuario usuario = repository.findByEmail(dto.getEmail());

		if (usuario == null) {
			throw new EntityNotFoundException("O email informado não foi encontrado.");
		}

		// gerar uma nova senha TEMPORÁRIA de forma randômica para o usuário..
		String novaSenha = PasswordHelper.generateRandomPassword();

		// Criptografa a nova senha e atribui ao usuário
		usuario.setSenha(Criptografia.criptografar(novaSenha));

		// salva o usuário no banco já com a senha alterada e criptografada
		repository.save(usuario);

		// chamando o método pra envio de email
		String response =  enviarNovaSenha(usuario, novaSenha);
		return response;
	
	}

	// método para realizar o envio da mensagem..
	private String enviarNovaSenha(Usuario usuario, String novaSenha) {

		try {
			String assunto = "Nova senha gerada com sucesso - Api de Clientes";
			String mensagem = "Olá, " + usuario.getNome() + "\n\n"
					+ "Sua nova senha foi gerada com sucesso, para acessar o sistema utilize a senha: " + novaSenha + "\n\n"
					+ "Att, \n" + "Equipe FRAGA TECH";

			// enviando o email.
			mailSender.sendMessage(usuario.getEmail(), assunto, mensagem);
			
			String response = "Uma nova senha foi gerada com sucesso. Verifique seu email.";
			return response;
			
		} catch (ServiceException e) {
			String response = "Erro: " + e.getMessage();
			return response;
		}
		
	}

}
