package com.test.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.api.entity.Departamento;
import com.test.api.repository.DepartamentoRepository;
import com.test.api.util.NumberUtils;

@Service
public class DepartamentoService {
	
	private final DepartamentoRepository departamentoRepository;
	
	@Autowired
	public DepartamentoService(DepartamentoRepository departamentoRepository) {
		this.departamentoRepository = departamentoRepository;
	}
	
	public List<Departamento> findAll(){
		return departamentoRepository.findAll();
	}
	
	public Departamento findById(Long id) {
		return departamentoRepository.findById(NumberUtils.getZeroIfNull(id).longValue())
				.orElse(null);
	}
}
