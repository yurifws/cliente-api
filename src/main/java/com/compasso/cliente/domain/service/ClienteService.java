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
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private CidadeService cidadeService;

	public List<Cliente> listar() {
		return clienteRepository.findAll();
	}

	public Cliente buscar(Long id) {
		return clienteRepository.findById(id).orElseThrow(() -> new ClienteNaoEncontradoException(id));
	}

	public List<Cliente> buscarPorNome(String nome) {
		return clienteRepository.findByNomeContaining(nome);
	}

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

	@Transactional
	public void remover(Long id) {
		clienteRepository.deleteById(id);
		clienteRepository.flush();
	}

}
