package com.compasso.cliente.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;

import com.compasso.cliente.domain.exception.EntidadeEmUsoException;
import com.compasso.cliente.domain.model.BaseEntity;
import com.compasso.cliente.domain.repository.IRepository;

import lombok.Getter;

@Getter
public abstract class ServiceGeneric<Classe extends BaseEntity, 
	Repository extends IRepository<Classe>> 
		implements IService<Classe>{

	@Autowired
	private Repository repository;
	
	public List<Classe> listar(){
		return repository.findAll();
	}
	
	public Classe buscar(Long id){
		return repository.findById(id).get();
	}
	
	@Transactional
	public Classe salvar(Classe estado) {
		return repository.save(estado);
	}
	
	@Transactional
	public void remover(Long id, String mensagem) {
		try {
			repository.deleteById(id);
			repository.flush();
		}catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(mensagem, id));
		}
	}
	
	
}
