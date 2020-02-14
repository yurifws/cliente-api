package com.compasso.cliente.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.compasso.cliente.domain.exception.EstadoNaoEncontradoException;
import com.compasso.cliente.domain.model.Estado;
import com.compasso.cliente.domain.repository.EstadoRepository;

@Service
public class EstadoService extends ServiceGeneric<Estado, EstadoRepository>{
	
	private static final String MSG_ESTADO_EM_USO = "Estado de código %d náo pode ser removido, pois está em uso.";
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	@Override
	public Estado buscar(Long id) {
		return estadoRepository.findById(id).orElseThrow(() -> new EstadoNaoEncontradoException(id));
	}

	@Override
	public List<Estado> buscarPorNome(String nome) {
		return estadoRepository.findByNomeContaining(nome);
	}
	
	public void remover(Long id) {
		remover(id, MSG_ESTADO_EM_USO);
	}

}
