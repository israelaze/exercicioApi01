package br.com.cotiinformatica.controllers;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.cotiinformatica.dtos.RecuperarSenhaPostDTO;
import br.com.cotiinformatica.entities.Usuario;
import br.com.cotiinformatica.exceptions.EntityNotFoundException;
import br.com.cotiinformatica.exceptions.ServiceException;
import br.com.cotiinformatica.helpers.PasswordHelper;
import br.com.cotiinformatica.repositories.UsuarioRepository;
import br.com.cotiinformatica.security.Criptografia;
import br.com.cotiinformatica.senders.MailSender;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Transactional
@Api(tags = "Recuperação de senha")
public class RecuperarSenhaController {

	// atributo para armazenar o endereço do serviço
	private static final String ENDPOINT = "/api/recuperarsenha";

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired 
	private MailSender mailSender;

	@CrossOrigin
	@RequestMapping(value = ENDPOINT, method = RequestMethod.POST)
	@ApiOperation(value = "recuperar")
	public ResponseEntity<String> post(@Valid @RequestBody RecuperarSenhaPostDTO dto) {

		try {
			
			//consultando o usuario no banco de dados atraves do email
			Usuario usuario = usuarioRepository.findByEmail(dto.getEmail());
			
			//verificar se o email esta cadastrado na base de dados..
			if(usuario != null) {
				
				//gerar uma nova senha TEMPORÁRIA de forma randômica para o usuário..
				String novaSenha = PasswordHelper.generateRandomPassword();
				
				//Criptografa a nova senha e atribui ao usuário
				usuario.setSenha(Criptografia.criptografar(novaSenha));
				
				//salva o usuário no banco já com a senha alterada e criptografada
				usuarioRepository.save(usuario);
				
				//enviar a senha TEMPORÁRIA para o email do usuário
				enviarNovaSenha(usuario, novaSenha);
				
				//gerando mensagem de sucesso
				return ResponseEntity
						.status(HttpStatus.OK) //HTTP 200
						.body("Uma nova senha foi gerada com sucesso. Verifique seu email.");
			}
			else {
				throw new EntityNotFoundException("O email informado não foi encontrado.");
			
			}
		}
		catch(ServiceException e) {
			return ResponseEntity
					.status(HttpStatus.INTERNAL_SERVER_ERROR) //HTTP 500
					.body("Erro: " + e.getMessage());
		}
	}
	
	//método para realizar o envio da mensagem..
	private void enviarNovaSenha(Usuario usuario, String novaSenha){
			
			String assunto = "Nova senha gerada com sucesso - COTI Informática API de Produtos";
			String mensagem = "Olá, " + usuario.getNome() + "\n\n"
							+ "Sua nova senha foi gerada com sucesso, para acessar o sistema utilize a senha: " + novaSenha
							+ "\n\n"
							+ "Att, \n"
							+ "Equipe COTI Informática";
			
			//enviando o email..- |objeto da classe de envio.método da classe de envio(destinatário, assunto, mensagem)
			mailSender.sendMessage(usuario.getEmail(), assunto, mensagem);
				
	}
}

