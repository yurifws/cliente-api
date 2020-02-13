package com.compasso.cliente.domain.exception;

public class NegocioException extends RuntimeException {

	private static final long serialVersionUID = 2652715466260011269L;

	public NegocioException(String mensagem){
		super(mensagem);
	}
	
	public NegocioException(String mensagem, Throwable cause){
		super(mensagem, cause);
	}
}
