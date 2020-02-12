package com.compasso.cliente.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.compasso.cliente.domain.exception.CidadeNaoEncontradoException;
import com.compasso.cliente.domain.exception.EntidadeEmUsoException;
import com.compasso.cliente.domain.exception.EntidadeNaoEncontradaException;
import com.compasso.cliente.domain.exception.NegocioException;
import com.compasso.cliente.domain.model.Cidade;
import com.compasso.cliente.domain.model.Estado;
import com.compasso.cliente.domain.repository.CidadeRepository;

@Service
public class CidadeService {
	
	private static final String MSG_CIDADE_EM_USO = "Cidade de código %d náo pode ser removido, pois está em uso.";
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	
	@Autowired
	private EstadoService estadoService;
	
	public List<Cidade> listar(){
		return cidadeRepository.findAll();
	}
	
	public Cidade buscar(Long id){
		return cidadeRepository.findById(id).orElseThrow(() -> new CidadeNaoEncontradoException(id));
	}
	
	public List<Cidade> buscarPorNome(String nome){
		return cidadeRepository.findByNomeContaining(nome);
	}
	
	public List<Cidade> buscarPorEstadoNome(String nome){
		return cidadeRepository.findByEstadoNomeContaining(nome);
	}
	
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
	
	@Transactional
	public void remover(Long id) {
		try {
			cidadeRepository.deleteById(id);
			cidadeRepository.flush();
		}catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(MSG_CIDADE_EM_USO, id));
		}
	}

}
