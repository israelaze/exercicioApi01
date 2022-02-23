package br.com.cotiinformatica.controllers;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.cotiinformatica.dtos.RecuperarSenhaPostDTO;
import br.com.cotiinformatica.exceptions.ServiceException;
import br.com.cotiinformatica.services.RecuperarSenhaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@Api(tags = "Recuperação de senha")
@RequestMapping(value = "/api/recuperarsenha")
public class RecuperarSenhaController {

	private final RecuperarSenhaService service;

	@CrossOrigin
	@PostMapping
	@ApiOperation(value = "recuperar")
	public ResponseEntity<String> post(@Valid @RequestBody RecuperarSenhaPostDTO dto) {

		try {
			String response = service.recuperarSenha(dto);
			return ResponseEntity.ok(response);

		} catch (ServiceException e) {
			return ResponseEntity.internalServerError().body(e.getMessage());
		}
	}

}
