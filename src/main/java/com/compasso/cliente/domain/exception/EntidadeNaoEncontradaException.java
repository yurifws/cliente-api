package com.compasso.cliente.domain.exception;

public abstract class EntidadeNaoEncontradaException  extends NegocioException{

	private static final long serialVersionUID = -5652446699249604648L;

	public EntidadeNaoEncontradaException(String mensagem){
		super(mensagem);
	}
}
