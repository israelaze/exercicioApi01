package br.com.cotiinformatica.controllers;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
<<<<<<< HEAD
=======
import org.springframework.web.bind.annotation.CrossOrigin;
>>>>>>> 411e16d64d240afed864f402495bcfec7c2de83f
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.cotiinformatica.dtos.UsuarioGetDTO;
import br.com.cotiinformatica.dtos.UsuarioPostDTO;
import br.com.cotiinformatica.exceptions.ServiceException;
import br.com.cotiinformatica.services.UsuarioService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;

<<<<<<< HEAD
=======
@CrossOrigin
>>>>>>> 411e16d64d240afed864f402495bcfec7c2de83f
@RestController
@AllArgsConstructor
@Api(tags = "Menu Usuários")
@RequestMapping(value = "/api/usuarios")
public class UsuariosController {

	private final UsuarioService service;

	@PostMapping
	@ApiOperation(value = "cadastrar")
	public ResponseEntity<UsuarioGetDTO> cadastrar(@Valid @RequestBody UsuarioPostDTO dto) {

		try {
			UsuarioGetDTO getDto = service.cadastrar(dto);
			return ResponseEntity.status(HttpStatus.CREATED).body(getDto);

		} catch (ServiceException e) {
			return ResponseEntity.internalServerError().build();
		}
	}

}