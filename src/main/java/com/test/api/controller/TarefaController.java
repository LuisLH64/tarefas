package com.test.api.controller;

import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.test.api.dto.TarefaDTO;
import com.test.api.entity.Tarefa;
import com.test.api.service.TarefaService;
import com.test.api.util.CustomResponseModel;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/tarefas")
public class TarefaController {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private final TarefaService tarefaService;
	
	@Autowired
	public TarefaController(TarefaService tarefaService) {
		this.tarefaService = tarefaService;
	}
	
	@GetMapping
	public ResponseEntity<?> findAll(){
		return ResponseEntity.ok(tarefaService.findAll());
	}
	
	@GetMapping("/pendentes")
	public ResponseEntity<?> tarefasPendentes(){
		List<Tarefa> tarefas = tarefaService.findAll();
		Collections.sort(tarefas, (d1, d2) -> d1.getPrazo().compareTo(d2.getPrazo()));
		tarefas = tarefas.stream()
			.filter(t -> t.getPessoa() == null)
			.collect(Collectors.toList());
		
		return ResponseEntity.ok(tarefas.subList(0, 3));
	}
	
	@PostMapping
	public ResponseEntity<?> insert(@Valid @RequestBody TarefaDTO tarefaDTO){
		try{
			tarefaService.insert(tarefaDTO);
			return ResponseEntity.created(new URI(""))
					.body(new CustomResponseModel(HttpStatus.CREATED.value(), "Tarefa inserida com sucesso!"));
		}
		catch (Exception e){
			logger.error(e.toString());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new CustomResponseModel(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()));
		}
	}
	
	@PutMapping("/alocar/{id}")
	public ResponseEntity<?> alocar(@PathVariable("id") Long idPessoa){
		try {
			tarefaService.alocar(idPessoa);
			return ResponseEntity.ok()
					.body(new CustomResponseModel(HttpStatus.OK.value(), "Tarefa alocada à pessoa com sucesso!"));
		} 
		catch (Exception e) {
			logger.error(e.toString());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new CustomResponseModel(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()));
		}
	}
	
	@PutMapping("/finalizar/{id}")
	public ResponseEntity<?> finalizar(@PathVariable Long id){
		try{
			tarefaService.finalizar(id);
			return ResponseEntity.ok()
					.body(new CustomResponseModel(HttpStatus.OK.value(), "Tarefa finalizada com sucesso!"));
		}
		catch (Exception e){
			logger.error(e.toString());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new CustomResponseModel(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()));
		}
	}
	
}
