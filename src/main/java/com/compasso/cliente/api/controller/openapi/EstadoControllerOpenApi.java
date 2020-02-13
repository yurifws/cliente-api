package com.compasso.cliente.api.controller.openapi;

import java.util.List;

import com.compasso.cliente.api.model.EstadoModel;
import com.compasso.cliente.api.model.input.EstadoInput;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "Estados")
public interface EstadoControllerOpenApi {

	@ApiOperation("Lista os estados")
	public List<EstadoModel> listar();
	
	@ApiOperation("Lista estado por ID")
	public EstadoModel buscar(Long id);
	
	@ApiOperation("Cadastra um estado")
	public EstadoModel salvar(EstadoInput estadoInput);
	@ApiOperation("Atualiza um estado por ID")
	public EstadoModel atualizar(Long id, EstadoInput estadoInput);

	@ApiOperation("Exclui um estado por ID")
	public void remover(Long id);
}
