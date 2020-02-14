package com.compasso.cliente.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.compasso.cliente.domain.model.BaseEntity;

public interface IRepository<Classe extends BaseEntity> extends JpaRepository<Classe, Long>{
	
}
