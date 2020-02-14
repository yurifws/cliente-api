package com.compasso.cliente.domain.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.compasso.cliente.domain.model.Cidade;

@Repository
public interface CidadeRepository extends IRepository<Cidade>{

	public List<Cidade> findByNomeContaining(String nome);
	public List<Cidade> findByEstadoNomeContaining(String nome);

}
