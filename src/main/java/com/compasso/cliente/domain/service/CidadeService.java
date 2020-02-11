package com.compasso.cliente.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.compasso.cliente.domain.exception.CidadeNaoEncontradoException;
import com.compasso.cliente.domain.exception.EntidadeEmUsoException;
import com.compasso.cliente.domain.model.Cidade;
import com.compasso.cliente.domain.repository.CidadeRepository;

@Service
public class CidadeService {
	
	private static final String MSG_CIDADE_EM_USO = "Cidade de código %d náo pode ser removido, pois está em uso.";
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	public List<Cidade> listar(){
		return cidadeRepository.findAll();
	}
	
	public Cidade buscar(Long id){
		return cidadeRepository.findById(id).orElseThrow(() -> new CidadeNaoEncontradoException(id));
	}
	
	@Transactional
	public Cidade salvar(Cidade cidade) {
		return cidadeRepository.save(cidade);
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
