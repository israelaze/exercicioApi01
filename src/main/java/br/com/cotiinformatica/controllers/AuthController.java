package br.com.cotiinformatica.controllers;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.cotiinformatica.dtos.AuthPostDTO;
import br.com.cotiinformatica.dtos.UsuarioGetDTO;
import br.com.cotiinformatica.exceptions.ServiceException;
import br.com.cotiinformatica.services.AuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;

@CrossOrigin
@RestController
@AllArgsConstructor
@Api(tags = "Login")
@RequestMapping(value = "/api/auth")
public class AuthController {
	
	private final AuthService service;
	
	@PostMapping
	@ApiOperation(value = "autenticar")
	public ResponseEntity<UsuarioGetDTO> autenticar(@Valid @RequestBody AuthPostDTO dto){
		
		try {
			UsuarioGetDTO getDto = service.autenticar(dto);
			return ResponseEntity.ok(getDto);
			
		}
		catch(ServiceException e) {
			return ResponseEntity.internalServerError().build();
			
		}
	
	}
}
