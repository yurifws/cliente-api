package com.compasso.cliente.domain.exception;

public class ClienteNaoEncontradoException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = -4643293859624782427L;
	
	private static final String MSG_CIDADE_NAO_ENCONTRADO = "Não existe um cadastro de cliente com código %d";

	public ClienteNaoEncontradoException(String mensagem) {
		super(mensagem);
	}

	public ClienteNaoEncontradoException(Long estadoId) {
		this(String.format(MSG_CIDADE_NAO_ENCONTRADO, estadoId));
	}
}
