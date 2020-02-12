package com.compasso.cliente.api.model.input;

import java.time.LocalDate;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClienteInput {

	@NotBlank
	private String nome;
	
	@NotBlank
	private String sexo;
	
	private LocalDate dataNascimento;

	private int idade;

	@Valid
	@NotNull
	private CidadeIdInput cidade;

}
