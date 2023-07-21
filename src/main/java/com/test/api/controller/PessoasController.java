package com.test.api.controller;

import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.test.api.dto.PessoaDTO;
import com.test.api.entity.Pessoa;
import com.test.api.service.PessoaService;
import com.test.api.util.CustomResponseModel;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/pessoas")
public class PessoasController {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private final PessoaService pessoaService;
	
	@Autowired
	public PessoasController(PessoaService pessoaService) {
		this.pessoaService = pessoaService;
	}
	
	@GetMapping
	public ResponseEntity<?> findAll(){
		return ResponseEntity.ok(pessoaService.findAll());
	}
	
	@PostMapping
	public ResponseEntity<?> insert(@Valid @RequestBody PessoaDTO pessoaDTO){
		try{
			pessoaService.insert(pessoaDTO);
			return ResponseEntity.created(new URI(""))
					.body(new CustomResponseModel(HttpStatus.CREATED.value(), "Pessoa inserida com sucesso!"));
		}
		catch (Exception e){
			logger.error(e.toString());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new CustomResponseModel(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()));
		}
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> update(@Valid @RequestBody PessoaDTO pessoaDTO, 
									@PathVariable Long id)
	{
		try{
			pessoaService.update(id, pessoaDTO);
			return ResponseEntity.ok()
					.body(new CustomResponseModel(HttpStatus.OK.value(), "Pessoa atualizada com sucesso!"));
		}
		catch (Exception e){
			logger.error(e.toString());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new CustomResponseModel(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()));
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		try{
			pessoaService.delete(id);
			return ResponseEntity.ok()
					.body(new CustomResponseModel(HttpStatus.OK.value(), "Pessoa excluída com sucesso!"));
		}
		catch (Exception e){
			logger.error(e.toString());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new CustomResponseModel(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()));
		}
	}
}
