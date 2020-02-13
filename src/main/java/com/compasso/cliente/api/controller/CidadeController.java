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

import com.compasso.cliente.api.assembler.CidadeAssembler;
import com.compasso.cliente.api.model.CidadeModel;
import com.compasso.cliente.api.model.input.CidadeInput;
import com.compasso.cliente.domain.model.Cidade;
import com.compasso.cliente.domain.service.CidadeService;

import io.swagger.annotations.Api;

@Api(tags = "Cidades")
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
	
	@GetMapping("/por-nome")
	public List<CidadeModel> buscarPorNome(@RequestParam String nome){
		return cidadeAssembler.toCollectionModel(cidadeService.buscarPorNome(nome));
	}
	
	@GetMapping("/por-estado")
	public List<CidadeModel> buscarPorEstadoNome(@RequestParam String nome){
		return cidadeAssembler.toCollectionModel(cidadeService.buscarPorEstadoNome(nome));
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CidadeModel salvar(@RequestBody @Valid CidadeInput cidadeInput){
		Cidade cidade = cidadeAssembler.toDomainObject(cidadeInput);
		return cidadeAssembler.toModel(cidadeService.salvar(cidade));
	}
	
	@PutMapping("/{id}")
	public CidadeModel atualizar(@PathVariable Long id, @RequestBody @Valid CidadeInput cidadeInput){
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
