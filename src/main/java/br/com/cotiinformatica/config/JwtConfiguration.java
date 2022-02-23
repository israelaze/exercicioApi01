// Classe principal de configuração do JWT. Essa classe defini como irá funcionar o JWT

package br.com.cotiinformatica.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import br.com.cotiinformatica.filters.JWTAuthorizationFilter;

@Configuration //classe de configuração do Spring
@EnableWebSecurity //configuração de autenticação
public class JwtConfiguration extends WebSecurityConfigurerAdapter {

	//método para realizar a configuração do JWT
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		//mapear a classe JwtAuthorizationFilter (segurança da API)
		http.csrf().disable().addFilterAfter(
				new JWTAuthorizationFilter(), //Classe que faz a segurança da API
				UsernamePasswordAuthenticationFilter.class)
				.authorizeRequests()
				.antMatchers("/api/usuarios").permitAll()  //permitir cadastrar usuários
				.antMatchers("/api/auth").permitAll() //permitir autenticação do usuário
				.antMatchers("/api/recuperarsenha").permitAll() //permitir recuperar senha de acesso
				//permitir o envio de parâmetros adicionais no protocolo HTTP como por ex: Header, Patch, et..
				.antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
				.anyRequest()
				.authenticated();		
	}	
	
	//configuração para liberar a documentação do SWAGGER
	private static final String[] SWAGGER = {
	            // -- Swagger UI v2
	            "/v2/api-docs",
	            "/swagger-resources",
	            "/swagger-resources/**",
	            "/configuration/ui",
	            "/configuration/security",
	            "/swagger-ui.html",
	            "/webjars/**",
	            // -- Swagger UI v3 (OpenAPI)
	            "/v3/api-docs/**",
	            "/swagger-ui/**"
	            // other public endpoints of your API may be appended to this array
	 };
		
	@Override
	public void configure(WebSecurity web) throws Exception {
	     web.ignoring().antMatchers(SWAGGER);
	}
}


