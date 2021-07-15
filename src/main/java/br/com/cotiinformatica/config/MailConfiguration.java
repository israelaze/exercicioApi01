//Configura e acessa a conta de email pra envio de mensagens

package br.com.cotiinformatica.config;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class MailConfiguration {

	// método para configurar as propriedades de envio de email..
	@Bean
	public JavaMailSender getJavaMailSender() {

		JavaMailSenderImpl mailSenderImpl = new JavaMailSenderImpl();

		// configuração do serviço SMTP (envio de email)
		mailSenderImpl.setHost("smtp.gmail.com");
		mailSenderImpl.setPort(587);

		// configuração da conta de email utilizada para enviar emails
		mailSenderImpl.setUsername("cotiaulasnoreply@gmail.com");
		mailSenderImpl.setPassword("coti123456");

		Properties props = mailSenderImpl.getJavaMailProperties();
		props.put("mail.transport.protocol", "smtp");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.debug", "true");

		return mailSenderImpl;
	}

}
