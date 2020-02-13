package com.compasso.cliente.api.model.input;

import java.time.LocalDate;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClienteInput {

	@ApiModelProperty(example = "João Pedro", required = true)
	@NotBlank
	private String nome;
	
	@ApiModelProperty(example = "M", required = true)
	@NotBlank
	private String sexo;
	
	@ApiModelProperty(example = "1995-12-26", required = true)
	@NotNull
	private LocalDate dataNascimento;

	@ApiModelProperty(example = "14")
	private int idade;

	@Valid
	@NotNull
	private CidadeIdInput cidade;

}
