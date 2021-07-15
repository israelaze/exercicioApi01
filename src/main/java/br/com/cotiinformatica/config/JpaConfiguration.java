package br.com.cotiinformatica.config;

import javax.persistence.EntityManagerFactory;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;


// Essa classe irá ler p arquivo persistence.xml e configurar o JPA / Hibernate para fazer o acesso ao banco de dados.

@Configuration //1
@EnableJpaRepositories(basePackages = { "br.com.cotiinformatica"}) //2
@EnableTransactionManagement //3
public class JpaConfiguration {
	
	@Bean //4
	public LocalEntityManagerFactoryBean entityManagerFactory() {
		
	//método que irá ler a configuração do arquivo persistence.xml através do nome:conexao_mysql

		LocalEntityManagerFactoryBean factoryBean = new LocalEntityManagerFactoryBean();
		
		factoryBean.setPersistenceUnitName("conexao_mysql");
		
		return factoryBean;
		
	}
	
	@Bean
	public JpaTransactionManager transactionManager (EntityManagerFactory entityManagerFactory) {
	
	//Método para permitir ao Hibernate transacionar o banco de dados, ou seja: Gravar, Excluir, Alterar e tambem consultar registros..
 
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		
		transactionManager.setEntityManagerFactory(entityManagerFactory);
		
		
		return transactionManager;
			
	}
	
}

/* 
 1 Classe de configuração do Spring
 2 Será capaz de reconhecer qualquer repositorio criado no projeto
 3 Habilitando para fazer transações em banco de dados (CRUD)
 4 
 */
