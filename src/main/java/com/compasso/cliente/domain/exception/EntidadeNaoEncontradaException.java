package com.compasso.cliente.domain.exception;

public class EntidadeNaoEncontradaException  extends NegocioException{

	private static final long serialVersionUID = -5652446699249604648L;

	public EntidadeNaoEncontradaException(String mensagem){
		super(mensagem);
	}
	
	public EntidadeNaoEncontradaException(String mensagem, Long estadoId) {
		this(String.format(mensagem, estadoId));
	}
}
