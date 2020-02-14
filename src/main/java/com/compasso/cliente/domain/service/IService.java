package com.compasso.cliente.domain.service;

import java.util.List;

import com.compasso.cliente.domain.model.BaseEntity;

public interface IService<Classe extends BaseEntity> {

	public Classe buscar(Long id);
	
	public List<Classe> buscarPorNome(String nome);
	
	public Classe salvar(Classe classe);
	
	public void remover(Long id, String mensagem);
}
