package com.compasso.cliente.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.compasso.cliente.api.model.CidadeModel;
import com.compasso.cliente.api.model.input.CidadeInput;
import com.compasso.cliente.domain.model.Cidade;

@Component
public class CidadeAssembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public CidadeModel toModel(Cidade cidade) {
		return modelMapper.map(cidade, CidadeModel.class);
	}
	
	public List<CidadeModel> toCollectionModel(List<Cidade> cidades) {
		return cidades.stream()
				.map(cidade -> toModel(cidade))
				.collect(Collectors.toList());
	}
	
	public Cidade toDomainObject(CidadeInput cidadeInput) {
		return modelMapper.map(cidadeInput, Cidade.class);
	}
	
	public void copyToDomainObject(CidadeInput cidadeInput, Cidade cidade) {	
		modelMapper.map(cidadeInput, cidade);
	}
	
}
