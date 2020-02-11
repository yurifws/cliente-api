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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.compasso.cliente.api.assembler.EstadoAssembler;
import com.compasso.cliente.api.model.EstadoModel;
import com.compasso.cliente.api.model.input.EstadoInput;
import com.compasso.cliente.domain.model.Estado;
import com.compasso.cliente.domain.service.EstadoService;

@RestController
@RequestMapping("/estados")
public class EstadoController {
	
	@Autowired
	private EstadoService estadoService;
	
	@Autowired
	private EstadoAssembler estadoAssembler;
	
	@GetMapping
	public List<EstadoModel> listar(){
		return estadoAssembler.toCollectionModel(estadoService.listar());
	}
	
	@GetMapping("/{id}")
	public EstadoModel buscar(@PathVariable Long id){
		return estadoAssembler.toModel(estadoService.buscar(id));
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Estado buscar(@RequestBody @Valid EstadoInput estadoInput){
		return estadoService.salvar(estadoAssembler.toDomainObject(estadoInput));
	}
	
	@PutMapping("/{id}")
	public Estado buscar(@PathVariable Long id, @RequestBody @Valid EstadoInput estadoInput){
		Estado estadoAtual = estadoService.buscar(id);
		estadoAssembler.copyToDomainObject(estadoInput, estadoAtual);
		return estadoService.salvar(estadoAtual);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id){
		Estado estadoAtual = estadoService.buscar(id);
		estadoService.remover(estadoAtual.getId());
	}

}
