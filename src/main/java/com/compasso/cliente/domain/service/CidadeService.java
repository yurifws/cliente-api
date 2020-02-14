package com.compasso.cliente.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.compasso.cliente.domain.exception.CidadeNaoEncontradoException;
import com.compasso.cliente.domain.exception.EntidadeNaoEncontradaException;
import com.compasso.cliente.domain.exception.NegocioException;
import com.compasso.cliente.domain.model.Cidade;
import com.compasso.cliente.domain.model.Estado;
import com.compasso.cliente.domain.repository.CidadeRepository;

@Service
public class CidadeService extends ServiceGeneric<Cidade, CidadeRepository>{
	
	private static final String MSG_CIDADE_EM_USO = "Cidade de código %d náo pode ser removido, pois está em uso.";
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private EstadoService estadoService;
	
	@Override
	public Cidade buscar(Long id) {
		return cidadeRepository.findById(id).orElseThrow(() -> new CidadeNaoEncontradoException(id));
	}
	
	@Override
	public List<Cidade> buscarPorNome(String nome){
		return cidadeRepository.findByNomeContaining(nome);
	}
	
	public List<Cidade> buscarPorEstadoNome(String nome){
		return cidadeRepository.findByEstadoNomeContaining(nome);
	}

	@Override
	@Transactional
	public Cidade salvar(Cidade cidade) {
		try {
			Long estadoId = cidade.getEstado().getId();
			Estado estado = estadoService.buscar(estadoId);
			cidade.setEstado(estado);
			return cidadeRepository.save(cidade);
		} catch (EntidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}
	
	public void remover(Long id) {
		remover(id, MSG_CIDADE_EM_USO);
	}

}
