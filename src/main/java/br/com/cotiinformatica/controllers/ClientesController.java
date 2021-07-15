package br.com.cotiinformatica.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.cotiinformatica.dtos.ClienteGetDTO;
import br.com.cotiinformatica.dtos.ClientePostDTO;
import br.com.cotiinformatica.dtos.ClientePutDTO;
import br.com.cotiinformatica.entities.Cliente;
import br.com.cotiinformatica.interfaces.IClienteRepository;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

@Controller //1
@Transactional // 2
public class ClientesController {
	
	//3 
	private static final String ENDPOINT= "/api/clientes"; 
	
	@Autowired // instanciando automaticamente a classe do Repositório(criando um objeto)
	private IClienteRepository clienteRepository;
	
	//Método para cadastro de Clientes
	@CrossOrigin
	@RequestMapping(value = ENDPOINT, method = RequestMethod.POST) //5
	@ResponseBody //6
	public ResponseEntity<String>post(@RequestBody ClientePostDTO dto) {
		
		try {
			
			//verificar se o CPF já está cadastrado no banco de dados
			if(clienteRepository.findByCpf(dto.getCpf()) != null) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body("O CPF informado já encontra-se cadastrado. Tente outro.");//HTTP 400
			}
			
			//resgatar os dados do DTO e transferi-los para a entidade
			Cliente cliente = new Cliente();
			cliente.setNome(dto.getNome());
			cliente.setCpf(dto.getCpf());
			cliente.setEmail(dto.getEmail());
			
			//gravar no banco de dados
			clienteRepository.save(cliente);
			
			//gerando mensagem de sucesso
			return ResponseEntity.status(HttpStatus.OK)
					.body("Cliente " + cliente.getNome() + " cadastrado com sucesso."); //HTTP 200
		}
		catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro: " + e.getMessage()); //HTTP 500
		}
	}
	
	// método para atualizar cliente
	@CrossOrigin
	@RequestMapping(value = ENDPOINT, method  = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<String> put(@RequestBody ClientePutDTO dto) {
		
		try {
			//procurar o cliente no banco de dados atraves do id..
			Optional<Cliente>result = clienteRepository.findById(dto.getIdCliente());
			
			//verificar se o cliente não foi encontrado e armazenando em result
			if(result == null || result.isEmpty()) {
				return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
						.body("Cliente não encontrado.");//HTTP 422
			}
			
			//objeto cliente recebe o cliente armazenado em result(obtendo dados do cliente)
			Cliente cliente = result.get(); 
			
			//atualizando os dados do cliente
			cliente.setNome(dto.getNome());
			cliente.setCpf(dto.getCpf());
			cliente.setEmail(dto.getEmail());
			
			//gravando novos dados no banco
			clienteRepository.save(cliente);
			
			//gerando mensagem de sucesso
			return ResponseEntity.status(HttpStatus.OK)
					.body("Cliente " + cliente.getNome() + " atualizado com sucesso."); //HTTP 200
			
		}
		catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Erro:" + e.getMessage()); //HTTP 500
		}
	
	}
	
	//Método para deletar 1 Cliente
	@CrossOrigin
	@RequestMapping(value = ENDPOINT + "/{idCliente}", method  = RequestMethod.DELETE)
	@ResponseBody
	public ResponseEntity<String> delete(@PathVariable("idCliente") Integer idCliente){
		
		try {
			//procurar o cliente no banco de dados atraves do id
			Optional<Cliente> result = clienteRepository.findById(idCliente);
			
			//verificar se o cliente não foi encontrado..
			if(result == null || result.isEmpty()) {
				return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
						.body("Cliente não encontrado.");
			}
			
			//objeto cliente recebe o cliente armazenado em result(obtendo dados do cliente)
			Cliente cliente = result.get();
			
			//excluindo no banco de dados
			clienteRepository.delete(cliente);
			
			//gerando mensagem de sucesso
			return ResponseEntity.status(HttpStatus.OK).body("Cliente " + cliente.getNome() + " excluído com sucesso.");
			
		}
		catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Erro:" + e.getMessage());
		}
		
	}
	//Método para consultar todos os Clientes
	@CrossOrigin
	@RequestMapping(value = ENDPOINT, method = RequestMethod.GET)
	@RequestBody
	public ResponseEntity<List<ClienteGetDTO>>get(){ // não retorna String e sim dados em forma de lista
		
		try {
			//declarando uma lista da classe ClienteGetDTO
			List<ClienteGetDTO> result = new ArrayList<ClienteGetDTO>();
			
			//consultar e percorrer os clientes obtidos no banco de dados..
			for(Cliente cliente : clienteRepository.findAll()) {
				
			//transferindo os dados do cliente pro objeto dto
				ClienteGetDTO dto = new ClienteGetDTO();
				dto.setIdCliente(cliente.getIdCliente());
				dto.setNome(cliente.getNome());
				dto.setCpf(cliente.getCpf());
				dto.setEmail(cliente.getEmail());
				
				//adicionar os clientes do objeto dto na lista, através do objeto result
				result.add(dto);
				
			}
			
			//retornando a lista, sem nenhuma mensagem de retorno
			return ResponseEntity.status(HttpStatus.OK).body(result);
			
		}
		catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(null); //HTTP 500
			
		}
			
	}
	
	//método para consultar 1 cliente atraves do ID
	@CrossOrigin
	@RequestMapping(value = ENDPOINT + "/{idCliente}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<ClienteGetDTO> getById(@PathVariable("idCliente") Integer idCliente) {
		
		try {
			//procurar o cliente no banco de dados atraves do id
			Optional<Cliente> result = clienteRepository.findById(idCliente);
			
			//verificar se o cliente não foi encontrado..
			if(result == null || result.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NO_CONTENT) //HTTP 204. Sucesso, porém retorna vazio
						.body(null);
			}
			else {
				
				//obter os dados do cliente
				Cliente cliente = result.get();
				
				//criando objeto para receber os dados
				ClienteGetDTO dto = new ClienteGetDTO(); //data transfer object
				
				//transferindo os dados pro obejeto dto
				dto.setIdCliente(cliente.getIdCliente());
				dto.setNome(cliente.getNome());
				dto.setCpf(cliente.getCpf());
				dto.setEmail(cliente.getEmail());

				//retornando os dados do cliente encontrado
				return ResponseEntity.status(HttpStatus.OK)
						.body(dto);
			}
		}
		catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);// 500
		}
	
	}
	
}



/*
 	1 Indica que a classe é um controlador
 	2
 	3 Criando um atributo constante(nome qualquer)para armazenar o endereço do serviço.
 	4 Método POST para cadastrar cliente
 	5 Anotação para mapear solicitações da web 
 	6 Define que o método retorna dados
 	
 	      Método PUT para atualizar cliente
 	7 Método DELETE para excluir cliente
 	8 Método GET para consultar cliente
 	
 	
 
 
 */