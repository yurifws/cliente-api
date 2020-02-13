package com.compasso.cliente.api.model;

import java.time.LocalDate;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClienteModel {
	
	@ApiModelProperty(example = "1")
	private Long id;
	
	@ApiModelProperty(example = "João Pedro")
	private String nome;
	
	@ApiModelProperty(example = "M")
	private char sexo;
	
	@ApiModelProperty(example = "1995-12-26")
	private LocalDate dataNascimento;
	
	@ApiModelProperty(example = "25")
	private int idade;
	
	private CidadeModel cidade;

}
