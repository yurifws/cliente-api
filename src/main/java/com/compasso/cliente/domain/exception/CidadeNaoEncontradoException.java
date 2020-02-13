package com.compasso.cliente.domain.exception;

public class CidadeNaoEncontradoException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = -4643293859624782427L;
	
	private static final String MSG_CIDADE_NAO_ENCONTRADO = "Não existe um cadastro de cidade com código %d";

	public CidadeNaoEncontradoException(String mensagem) {
		super(mensagem);
	}

	public CidadeNaoEncontradoException(Long estadoId) {
		this(String.format(MSG_CIDADE_NAO_ENCONTRADO, estadoId));
	}
}
