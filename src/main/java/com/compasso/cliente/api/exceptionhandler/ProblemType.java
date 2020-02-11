package com.compasso.cliente.api.exceptionhandler;

import lombok.Getter;

@Getter
public enum ProblemType {
	MENSAGEM_INCOMPREENSIVEL("/mensagem-incompreensivel", "Mensagem incompreensível"),
	RECURSO_NAO_ENCONTRADO("/recurso-nao-encontrado", "Recurso não encontrado"),
	ENTIDADE_EM_USO("/entidade-em-uso", "Entidade em uso"),
	ERRO_NEGOCIO("/erro-negocio", "Violação de regra de negócio");
	
	private String uri;
	private String title;
	
	private ProblemType(String path, String title) {
		this.uri = String.format("https://cliente-api.com.br%s", path);
		this.title = title;
	}
}
