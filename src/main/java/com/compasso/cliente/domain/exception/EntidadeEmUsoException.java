package com.compasso.cliente.domain.exception;

public class EntidadeEmUsoException extends NegocioException {

	private static final long serialVersionUID = -6576658427642688678L;

	public EntidadeEmUsoException(String mensagem){
		super(mensagem);
	}

}
