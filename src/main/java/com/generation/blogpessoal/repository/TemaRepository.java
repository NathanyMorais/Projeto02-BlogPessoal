package com.generation.blogpessoal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.generation.blogpessoal.model.Tema;

public interface TemaRepository extends JpaRepository<Tema, Long>{
	
	public List<Tema> findAllByDescricaoContainingIgnoreCase(@Param("descricao") String descricao); //Query Method - um método personalizado
	
	// A anotação @Param é utilizada para definir que uma variável String é um parâmetro da consulta
}
