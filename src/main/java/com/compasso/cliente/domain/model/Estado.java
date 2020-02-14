package com.compasso.cliente.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "tb_estado")
public class Estado extends BaseEntity{
	
	@Column(nullable = false)
	private String nome;

}
