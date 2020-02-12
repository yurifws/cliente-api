package com.compasso.cliente.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.compasso.cliente.domain.model.Cidade;

@Repository
public interface CidadeRepository extends JpaRepository<Cidade, Long>{
	
	public List<Cidade> findByNomeContaining(String nome);
	public List<Cidade> findByEstadoNomeContaining(String nome);

}
