package br.com.cotiinformatica.interfaces;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.cotiinformatica.entities.Usuario;

@Repository
public interface IUsuarioRepository extends CrudRepository<Usuario, Integer>{ //nome da entidade, tipo do Id

	//Método que busque 1 Usuário pelo email 
	@Query("from Usuario u where u.email = :param") // JPQL
	public Usuario findByEmail(@Param("param")String email);
	
	//Método que busque 1 Usuário pelo email e senha
	@Query("from Usuario u where u.email = :param1 and u.senha = :param2") //JPQL
	public Usuario findByEmailAndSenha(@Param("param1") String email, @Param("param2") String senha);
}
