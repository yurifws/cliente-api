package com.compasso.cliente.api.controller.openapi;

import java.util.List;

import com.compasso.cliente.api.model.CidadeModel;
import com.compasso.cliente.api.model.input.CidadeInput;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = "Cidades")
public interface CidadeControllerOpenApi {

	@ApiOperation("Lista as cidades")
	public List<CidadeModel> listar();

	@ApiOperation("Lista cidade por ID")
	public CidadeModel buscar(
			@ApiParam(value = "ID de uma cidade", example = "1") Long id);
	
	@ApiOperation("Lista cidades por nome")
	public List<CidadeModel> buscarPorNome(
			@ApiParam(value = "Nome de uma cidade", example = "1") String nome);
	
	@ApiOperation("Lista cidades por nome do estado")
	public List<CidadeModel> buscarPorEstadoNome(
			@ApiParam(value = "Nome de um estado", example = "1") String nome);

	@ApiOperation("Cadastra uma cidade")
	public CidadeModel salvar(
			@ApiParam(name = "corpo",value = "Representação de cidade com novos dados") CidadeInput cidadeInput);

	@ApiOperation("Atualiza uma cidade por ID")
	public CidadeModel atualizar(@ApiParam(value = "ID de uma cidade", example = "1") Long id, 
			@ApiParam(name = "corpo",value = "Representação de cidade com novos dados") CidadeInput cidadeInput);
	
	@ApiOperation("Exclui uma cidade por ID")
	public void remover(@ApiParam(value = "ID de uma cidade", example = "1") Long id);
}
