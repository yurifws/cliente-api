package com.compasso.cliente.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.compasso.cliente.api.model.ClienteModel;
import com.compasso.cliente.api.model.input.ClienteInput;
import com.compasso.cliente.domain.model.Cidade;
import com.compasso.cliente.domain.model.Cliente;

@Component
public class ClienteAssembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public ClienteModel toModel(Cliente cliente) {
		return modelMapper.map(cliente, ClienteModel.class);
	}
	
	public List<ClienteModel> toCollectionModel(List<Cliente> clientes) {
		return clientes.stream()
				.map(cliente -> toModel(cliente))
				.collect(Collectors.toList());
	}
	
	public Cliente toDomainObject(ClienteInput clienteInput) {
		return modelMapper.map(clienteInput, Cliente.class);
	}
	
	public void copyToDomainObject(ClienteInput clienteInput, Cliente cliente) {	
		cliente.setCidade(new Cidade());
		modelMapper.map(clienteInput, cliente);
	}
	
}
