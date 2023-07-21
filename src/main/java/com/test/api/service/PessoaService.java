package com.test.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.api.dto.PessoaDTO;
import com.test.api.entity.Departamento;
import com.test.api.entity.Pessoa;
import com.test.api.repository.PessoaRepository;
import com.test.api.util.NumberUtils;

@Service
public class PessoaService {
	
	private final PessoaRepository pessoaRepository;
	private final DepartamentoService departamentoService;
	
	@Autowired
	public PessoaService(PessoaRepository pessoaRepository,
						 DepartamentoService departamentoService) 
	{
		this.pessoaRepository = pessoaRepository;
		this.departamentoService = departamentoService;
	}
	
	public List<Pessoa> findAll(){
		return pessoaRepository.findAll();
	}
	
	public Pessoa findById(Long id){
		return pessoaRepository.findById(NumberUtils.getZeroIfNull(id).longValue())
				.orElse(null);
	}
	
	public void insert(PessoaDTO pessoaDTO) {
		Pessoa pessoa = new Pessoa();
		Departamento departamento = departamentoService.findById(pessoaDTO.getIdDepartamento());
		
		pessoa.setNome(pessoaDTO.getNome());
		pessoa.setDepartamento(departamento);
		
		pessoaRepository.save(pessoa);
	}
	
	public void update(Long id, PessoaDTO pessoaDTO) {
		Pessoa pessoa = pessoaRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Pessoa não encontrada"));
		Departamento departamento = departamentoService.findById(pessoaDTO.getIdDepartamento());
		
		pessoa.setNome(pessoaDTO.getNome());
		pessoa.setDepartamento(departamento);
			
		pessoaRepository.save(pessoa);

	}
	
	public void delete(Long id) {
		if(pessoaRepository.findById(id).isEmpty())
			throw new IllegalArgumentException("Pessoa não encontrada");
		
		pessoaRepository.deleteById(id);
	}
}
