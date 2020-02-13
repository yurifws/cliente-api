package com.compasso.cliente.api.model;

import java.time.LocalDate;

import com.compasso.cliente.domain.model.Cidade;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClienteModel {
	
	private Long id;
	private String nome;
	private char sexo;
	private LocalDate dataNascimento;
	private int idade;
	private Cidade cidade;

}
