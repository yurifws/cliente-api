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

import com.compasso.cliente.api.assembler.CidadeAssembler;
import com.compasso.cliente.api.model.CidadeModel;
import com.compasso.cliente.api.model.input.CidadeInput;
import com.compasso.cliente.domain.model.Cidade;
import com.compasso.cliente.domain.service.CidadeService;

@RestController
@RequestMapping("/cidades")
public class CidadeController {
	
	@Autowired
	private CidadeService cidadeService;
	
	@Autowired
	private CidadeAssembler cidadeAssembler;
	
	@GetMapping
	public List<CidadeModel> listar(){
		return cidadeAssembler.toCollectionModel(cidadeService.listar());
	}
	
	@GetMapping("/{id}")
	public CidadeModel buscar(@PathVariable Long id){
		return cidadeAssembler.toModel(cidadeService.buscar(id));
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CidadeModel buscar(@RequestBody @Valid CidadeInput cidadeInput){
		Cidade cidade = cidadeAssembler.toDomainObject(cidadeInput);
		return cidadeAssembler.toModel(cidadeService.salvar(cidade));
	}
	
	@PutMapping("/{id}")
	public CidadeModel buscar(@PathVariable Long id, @RequestBody @Valid CidadeInput cidadeInput){
		Cidade cidadeAtual = cidadeService.buscar(id);
		cidadeAssembler.copyToDomainObject(cidadeInput, cidadeAtual);
		return cidadeAssembler.toModel(cidadeService.salvar(cidadeAtual));
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id){
		Cidade cidadeAtual = cidadeService.buscar(id);
		cidadeService.remover(cidadeAtual.getId());
	}

}
