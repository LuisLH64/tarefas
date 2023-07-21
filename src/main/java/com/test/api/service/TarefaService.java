package com.test.api.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.api.dto.PessoaDTO;
import com.test.api.dto.TarefaDTO;
import com.test.api.entity.Departamento;
import com.test.api.entity.Pessoa;
import com.test.api.entity.Tarefa;
import com.test.api.repository.TarefaRepository;
import com.test.api.util.NumberUtils;

@Service
public class TarefaService {
	
	private final TarefaRepository tarefaRepository;
	private final PessoaService pessoaService;
	private final DepartamentoService departamentoService;
	
	@Autowired
	public TarefaService(TarefaRepository tarefaRepository,
						 PessoaService pessoaService,
						 DepartamentoService departamentoService) 
	{
		this.tarefaRepository = tarefaRepository;
		this.pessoaService = pessoaService;
		this.departamentoService = departamentoService;
	}
	
	public List<Tarefa> findAll(){
		return tarefaRepository.findAll();
	}
	
	public void insert(TarefaDTO tarefaDTO) {
		Pessoa pessoa = pessoaService.findById(tarefaDTO.getIdPessoa());
		Departamento departamento = departamentoService.findById(tarefaDTO.getIdDepartamento());
		Tarefa tarefa = new Tarefa();
		
		tarefa.setTitulo(tarefaDTO.getTitulo());
		tarefa.setDescricao(tarefaDTO.getDescricao());
		tarefa.setPrazo(tarefaDTO.getPrazo());
		tarefa.setDepartameto(departamento);
		tarefa.setDuracao(tarefaDTO.getDuracao());
		tarefa.setPessoa(pessoa);
		tarefa.setFinalizado(tarefaDTO.isFinalizado());
		
		tarefaRepository.save(tarefa);		
	}
	
	public void alocar(Long idPessoa) {
		Pessoa pessoa = pessoaService.findById(NumberUtils.getZeroIfNull(idPessoa).longValue());
		
		if(pessoa == null) {
			throw new IllegalArgumentException("Pessoa não encontrada");
		}
		
		List<Tarefa> tarefas = findAll()
				.stream()
				.filter(t -> t.getDepartameto().getId() == pessoa.getDepartamento().getId())
				.filter(t -> t.getPessoa() == null)
				.collect(Collectors.toList());
		
		if(tarefas.isEmpty()) {
			throw new IllegalArgumentException("Não há tarefas disponíveis para essa pessoa");
		}
		
		Tarefa tarefa = tarefas.get(0);
		tarefa.setPessoa(pessoa);
		
		tarefaRepository.save(tarefa);
	}
	
	public void finalizar(Long id) {
		Tarefa tarefa = tarefaRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Tarefa não encontrada"));
		tarefa.setFinalizado(true);
	
		tarefaRepository.save(tarefa);

	}
	
}
