package br.com.cotiinformatica.controllers;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
<<<<<<< HEAD
=======
import org.springframework.web.bind.annotation.CrossOrigin;
>>>>>>> 411e16d64d240afed864f402495bcfec7c2de83f
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.cotiinformatica.dtos.AuthGetDTO;
import br.com.cotiinformatica.dtos.AuthPostDTO;
import br.com.cotiinformatica.exceptions.ServiceException;
import br.com.cotiinformatica.services.AuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;

<<<<<<< HEAD
=======
@CrossOrigin
>>>>>>> 411e16d64d240afed864f402495bcfec7c2de83f
@RestController
@AllArgsConstructor
@Api(tags = "Login")
@RequestMapping(value = "/api/auth")
public class AuthController {
	
	private final AuthService service;
	
	@PostMapping
	@ApiOperation(value = "autenticar")
	public ResponseEntity<AuthGetDTO> autenticar(@Valid @RequestBody AuthPostDTO dto){
		
		try {
			AuthGetDTO getDto = service.autenticar(dto);
<<<<<<< HEAD
			return ResponseEntity.ok(getDto);	
		}
		catch(ServiceException e) {
			return ResponseEntity.internalServerError().build();	
=======
			return ResponseEntity.ok(getDto);
			
		}
		catch(ServiceException e) {
			return ResponseEntity.internalServerError().build();
			
>>>>>>> 411e16d64d240afed864f402495bcfec7c2de83f
		}
	}
}
