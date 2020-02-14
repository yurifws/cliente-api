package com.compasso.cliente.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.compasso.cliente.domain.exception.ClienteNaoEncontradoException;
import com.compasso.cliente.domain.exception.EntidadeNaoEncontradaException;
import com.compasso.cliente.domain.exception.NegocioException;
import com.compasso.cliente.domain.model.Cidade;
import com.compasso.cliente.domain.model.Cliente;
import com.compasso.cliente.domain.repository.ClienteRepository;

@Service
public class ClienteService extends ServiceGeneric<Cliente, ClienteRepository>{

	private static final String MSG_CLIENTE_EM_USO = "Cliente de código %d náo pode ser removido, pois está em uso.";
	
	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private CidadeService cidadeService;

	@Override
	public Cliente buscar(Long id) {
		return clienteRepository.findById(id).orElseThrow(() -> new ClienteNaoEncontradoException(id));
	}
	
	@Override
	public List<Cliente> buscarPorNome(String nome){
		return clienteRepository.findByNomeContaining(nome);
	}

	@Override
	@Transactional
	public Cliente salvar(Cliente cliente) {
		try {
			Long cidadeId = cliente.getCidade().getId();
			Cidade cidade = cidadeService.buscar(cidadeId);
			cliente.setCidade(cidade);
			cliente.obterIdade();
			return clienteRepository.save(cliente);
		} catch (EntidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}

	public void remover(Long id) {
		remover(id, MSG_CLIENTE_EM_USO);
	}

}
