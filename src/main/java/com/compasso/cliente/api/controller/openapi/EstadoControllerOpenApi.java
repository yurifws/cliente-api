package com.compasso.cliente.api.controller.openapi;

import java.util.List;

import com.compasso.cliente.api.model.EstadoModel;
import com.compasso.cliente.api.model.input.EstadoInput;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = "Estados")
public interface EstadoControllerOpenApi {

	@ApiOperation("Lista os estados")
	public List<EstadoModel> listar();
	
	@ApiOperation("Lista estado por ID")
	public EstadoModel buscar(
			@ApiParam(value = "ID de um estado", example = "1") Long id);
	
	@ApiOperation("Cadastra um estado")
	public EstadoModel salvar(
			@ApiParam(name = "corpo",value = "Representação de cliente com novos dados") EstadoInput estadoInput);
	
	@ApiOperation("Atualiza um estado por ID")
	public EstadoModel atualizar(@ApiParam(value = "ID de um estado", example = "1") Long id, 
			@ApiParam(name = "corpo",value = "Representação de cliente com novos dados") EstadoInput estadoInput);

	@ApiOperation("Exclui um estado por ID")
	public void remover(@ApiParam(value = "ID de um estado", example = "1") Long id);
}
