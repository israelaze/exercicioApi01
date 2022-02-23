package br.com.cotiinformatica.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.cotiinformatica.entities.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
	
	// Busca um cliente pelo seu cpf 
	@Query("from Cliente c where c.cpf = :param") //JPQL
	Cliente findByCpf(@Param("param") String cpf);
	
}

/* JPQL – JAVA PERSISTENCE QUERY LANGUAGE
Sintaxe para escrita de consultas em aplicações Java que fazem acesso a
banco de dados com Hibernate e JPA. Ou seja, substitui o uso do SQL para
escrita de consultas.  */