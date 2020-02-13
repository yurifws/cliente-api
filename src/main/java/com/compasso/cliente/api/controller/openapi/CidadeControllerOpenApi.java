package com.compasso.cliente.api.controller.openapi;

import java.util.List;

import com.compasso.cliente.api.model.CidadeModel;
import com.compasso.cliente.api.model.input.CidadeInput;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "Cidades")
public interface CidadeControllerOpenApi {

	@ApiOperation("Lista as cidades")
	public List<CidadeModel> listar();

	@ApiOperation("Lista cidade por ID")
	public CidadeModel buscar(Long id);
	
	@ApiOperation("Lista cidades por nome")
	public List<CidadeModel> buscarPorNome(String nome);
	
	@ApiOperation("Lista cidades por nome do estado")
	public List<CidadeModel> buscarPorEstadoNome(String nome);

	@ApiOperation("Cadastra uma cidade")
	public CidadeModel salvar(CidadeInput cidadeInput);

	@ApiOperation("Atualiza uma cidade por ID")
	public CidadeModel atualizar(Long id, CidadeInput cidadeInput);
	
	@ApiOperation("Exclui uma cidade por ID")
	public void remover(Long id);
}
