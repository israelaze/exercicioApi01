package br.com.cotiinformatica.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.cotiinformatica.dtos.ClienteGetDTO;
import br.com.cotiinformatica.dtos.ClientePostDTO;
import br.com.cotiinformatica.dtos.ClientePutDTO;
import br.com.cotiinformatica.entities.Cliente;
import br.com.cotiinformatica.exceptions.BadRequestException;
import br.com.cotiinformatica.exceptions.EntityNotFoundException;
import br.com.cotiinformatica.exceptions.ServiceException;
import br.com.cotiinformatica.repositories.ClienteRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


@RestController
@Transactional
@RequestMapping(value = "/api/clientes")
@Api(tags = "Menu Clientes")
public class ClientesController {

	@Autowired
	private ClienteRepository clienteRepository;

	@CrossOrigin
	@PostMapping
	@ApiOperation(value = "cadastrar")
	public ResponseEntity<String> post(@Valid @RequestBody ClientePostDTO dto) {

		try {

			// verificar se o CPF já está cadastrado no banco de dados
			if (clienteRepository.findByCpf(dto.getCpf()) != null) {
				throw new BadRequestException("O CPF informado já encontra-se cadastrado. Tente outro."); // HTTP 400
			}

			// resgatar os dados do DTO e transferi-los para a entidade
			Cliente cliente = new Cliente();
			cliente.setNome(dto.getNome());
			cliente.setCpf(dto.getCpf());
			cliente.setEmail(dto.getEmail());

			// gravar no banco de dados
			clienteRepository.save(cliente);

			// gerando mensagem de sucesso
			return ResponseEntity.status(HttpStatus.CREATED)
					.body("Cliente " + cliente.getNome() + " cadastrado com sucesso."); // HTTP 201
		} catch (ServiceException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro: " + e.getMessage()); // HTTP 500
		}
	}

	// Método para consultar todos os Clientes
	@CrossOrigin
	@GetMapping
	@ApiOperation(value = "listar todos")
	public ResponseEntity<List<ClienteGetDTO>> get() { // não retorna String e sim dados em forma de lista

		try {
			// declarando uma lista da classe ClienteGetDTO
			List<ClienteGetDTO> result = new ArrayList<ClienteGetDTO>();

			// consultar e percorrer os clientes obtidos no banco de dados..
			for (Cliente cliente : clienteRepository.findAll()) {

				// transferindo os dados do cliente pro objeto dto
				ClienteGetDTO dto = new ClienteGetDTO();
				dto.setIdCliente(cliente.getIdCliente());
				dto.setNome(cliente.getNome());
				dto.setCpf(cliente.getCpf());
				dto.setEmail(cliente.getEmail());

				// adicionar os clientes do objeto dto na lista, através do objeto result
				result.add(dto);

			}

			// retornando a lista, sem nenhuma mensagem de retorno
			return ResponseEntity.status(HttpStatus.OK).body(result);

		} catch (ServiceException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null); // HTTP 500

		}

	}

	// método para consultar 1 cliente atraves do ID
	@CrossOrigin
	@GetMapping(value = "/{idCliente}")
	@ApiOperation(value = "buscar pelo ID")
	public ResponseEntity<ClienteGetDTO> getById(@PathVariable("idCliente") Integer idCliente) {

		try {
			// procurar o cliente no banco de dados atraves do id
			Optional<Cliente> result = clienteRepository.findById(idCliente);

			// verificar se o cliente não foi encontrado..
			if (result == null || result.isEmpty()) {
				throw new EntityNotFoundException("Cliente não encontrado!"); // HTTP 404
					
			} else {

				// obter os dados do cliente
				Cliente cliente = result.get();

				// criando objeto para receber os dados
				ClienteGetDTO dto = new ClienteGetDTO(); // data transfer object

				// transferindo os dados pro obejeto dto
				dto.setIdCliente(cliente.getIdCliente());
				dto.setNome(cliente.getNome());
				dto.setCpf(cliente.getCpf());
				dto.setEmail(cliente.getEmail());

				// retornando os dados do cliente encontrado
				return ResponseEntity.status(HttpStatus.OK).body(dto);
			}
		} catch (ServiceException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);// 500
		}

	}

	// método para atualizar cliente
	@CrossOrigin
	@PutMapping
	@ApiOperation(value = "atualizar")
	public ResponseEntity<String> put(@Valid @RequestBody ClientePutDTO dto) {

		try {
			// procurar o cliente no banco de dados atraves do id..
			Optional<Cliente> result = clienteRepository.findById(dto.getIdCliente());

			if (result == null || result.isEmpty()) {
				throw new EntityNotFoundException("Cliente não encontrado!");// HTTP 404
			}

			// objeto cliente recebe o cliente armazenado em result(obtendo dados do
			// cliente)
			Cliente cliente = result.get();

			// atualizando os dados do cliente
			cliente.setNome(dto.getNome());
			cliente.setEmail(dto.getEmail());

			// gravando novos dados no banco
			clienteRepository.save(cliente);

			// gerando mensagem de sucesso
			return ResponseEntity.status(HttpStatus.OK)
					.body("Cliente " + cliente.getNome() + " atualizado com sucesso."); // HTTP 200

		} catch (ServiceException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro:" + e.getMessage()); // HTTP 500
		}

	}

	// Método para deletar 1 Cliente
	@CrossOrigin
	@DeleteMapping(value = "/{idCliente}")
	@ApiOperation(value = "excluir")
	public ResponseEntity<String> delete(@PathVariable("idCliente") Integer idCliente) {

		try {
			// procurar o cliente no banco de dados atraves do id
			Optional<Cliente> result = clienteRepository.findById(idCliente);

			// verificar se o cliente não foi encontrado..
			if (result == null || result.isEmpty()) {
				throw new EntityNotFoundException("Cliente não encontrado!");
			}

			// objeto cliente recebe o cliente armazenado em result(obtendo dados do
			// cliente)
			Cliente cliente = result.get();

			// excluindo no banco de dados
			clienteRepository.delete(cliente);

			// gerando mensagem de sucesso
			return ResponseEntity.status(HttpStatus.OK).body("Cliente " + cliente.getNome() + " excluído com sucesso.");

		} catch (ServiceException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro:" + e.getMessage());
		}

	}

}
