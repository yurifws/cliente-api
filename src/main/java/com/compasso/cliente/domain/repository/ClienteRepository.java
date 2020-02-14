package com.compasso.cliente.domain.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.compasso.cliente.domain.model.Cliente;

@Repository
public interface ClienteRepository extends IRepository<Cliente>{

	public List<Cliente> findByNomeContaining(String nome);
}
