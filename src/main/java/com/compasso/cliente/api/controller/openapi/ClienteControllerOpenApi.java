package com.compasso.cliente.api.controller.openapi;

import java.util.List;

import com.compasso.cliente.api.model.ClienteModel;
import com.compasso.cliente.api.model.input.ClienteInput;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = "Clientes")
public interface ClienteControllerOpenApi {

	@ApiOperation("Lista os clientes")
	public List<ClienteModel> listar();
	
	@ApiOperation("Lista cliente por ID")
	public ClienteModel buscar(
			@ApiParam(value = "ID de um cliente", example = "1") Long id);
	
	@ApiOperation("Lista clientes por nome")
	public List<ClienteModel> buscarPorNome(
			@ApiParam(value = "Nome de um cliente", example = "1") String nome);
	
	@ApiOperation("Cadastra um cliente")
	public ClienteModel salvar(ClienteInput clienteInput);

	@ApiOperation("Atualiza um cliente por ID")
	public ClienteModel atualizar(Long id, ClienteInput clienteInput);

	@ApiOperation("Exclui um cliente por ID")
	public void remover(Long id);
}
