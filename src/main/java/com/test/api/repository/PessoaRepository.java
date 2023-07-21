package com.test.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.test.api.entity.Pessoa;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
	static final String ALL = "SELECT * FROM pessoa";
	
	@Query(value = ALL, nativeQuery = true)
	List<Pessoa> findAll();
}
