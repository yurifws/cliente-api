package com.compasso.cliente.api.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClienteModel {
	
	private Long id;
	private String nome;
	private EstadoModel estado;

}
