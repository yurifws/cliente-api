package com.compasso.cliente.domain.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.compasso.cliente.domain.model.Estado;

@Repository
public interface EstadoRepository extends IRepository<Estado>{

	public List<Estado> findByNomeContaining(String nome);
}
