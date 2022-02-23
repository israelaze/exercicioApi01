// Classe para fazer o envio de email a partir de uma conta já configurada
package br.com.cotiinformatica.senders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class MailSender {

	// injeção de dependência da configuração de email feita na classe 'MailConfiguration'
	@Autowired
	private JavaMailSender javaMailSender;

	// método para realizar o envio do email..
	public void sendMessage(String destinatario, String assunto, String mensagem) {

		SimpleMailMessage mailMessage = new SimpleMailMessage();

		mailMessage.setTo(destinatario);
		mailMessage.setSubject(assunto);
		mailMessage.setText(mensagem);

		javaMailSender.send(mailMessage);
	}
}
