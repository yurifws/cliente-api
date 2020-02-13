package com.compasso.cliente.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.compasso.cliente.api.assembler.ClienteAssembler;
import com.compasso.cliente.api.controller.openapi.ClienteControllerOpenApi;
import com.compasso.cliente.api.model.ClienteModel;
import com.compasso.cliente.api.model.input.ClienteInput;
import com.compasso.cliente.domain.model.Cliente;
import com.compasso.cliente.domain.service.ClienteService;


@RestController
@RequestMapping("/clientes")
public class ClienteController  implements ClienteControllerOpenApi{
	
	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private ClienteAssembler cienteAssembler;
	
	@GetMapping
	public List<ClienteModel> listar(){
		return cienteAssembler.toCollectionModel(clienteService.listar());
	}
	
	@GetMapping("/{id}")
	public ClienteModel buscar(@PathVariable Long id){
		return cienteAssembler.toModel(clienteService.buscar(id));
	}
	
	@GetMapping("/por-nome")
	public List<ClienteModel> buscarPorNome(@RequestParam String nome){
		return cienteAssembler.toCollectionModel(clienteService.buscarPorNome(nome));
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ClienteModel salvar(@RequestBody @Valid ClienteInput clienteInput){
		Cliente cliente = cienteAssembler.toDomainObject(clienteInput);
		return cienteAssembler.toModel(clienteService.salvar(cliente));
	}

	@PutMapping("/{id}")
	public ClienteModel atualizar(@PathVariable Long id, @RequestBody @Valid ClienteInput clienteInput){
		Cliente clienteAtual = clienteService.buscar(id);
		cienteAssembler.copyToDomainObject(clienteInput, clienteAtual);
		return cienteAssembler.toModel(clienteService.salvar(clienteAtual));
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id){
		Cliente clienteAtual = clienteService.buscar(id);
		clienteService.remover(clienteAtual.getId());
	}

}
