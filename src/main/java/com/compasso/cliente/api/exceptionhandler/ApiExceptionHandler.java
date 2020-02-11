package com.compasso.cliente.api.exceptionhandler;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.compasso.cliente.domain.exception.EntidadeNaoEncontradaException;
import com.compasso.cliente.domain.exception.NegocioException;
import com.fasterxml.jackson.databind.JsonMappingException.Reference;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

	public static final String MSG_ERRO_GENERICA_USUARIO_FINAL = 
			"Ocorreu um erro interno inesperado no sistema. Tente novamente e se "
			+ "o problema persistir, entre em contato com o administrador do sistema.";
	
	
	@Override
	protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		if (ex instanceof MethodArgumentTypeMismatchException) {
			return handleMethodArgumentTypeMismatchException((MethodArgumentTypeMismatchException) ex, headers, status, request);
		}
		return super.handleTypeMismatch(ex, headers, status, request);
	}
	
	private ResponseEntity<Object> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex, 
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		ProblemType problemType = ProblemType.PARAMETRO_INVALIDO;
		String detail = String.format(
				"O parâmetro '%s' recebeu o valor '%s' que é de um tipo inválido. "
				+ "Corrija e informe um valor compatível com o tipo %s.",
				ex.getName(), ex.getValue(), ex.getRequiredType().getSimpleName());
		
		Problem problem = createProblemBuilder(status, problemType, detail, MSG_ERRO_GENERICA_USUARIO_FINAL).build();
		return handleExceptionInternal(ex, problem, headers, status, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		Throwable rootCause = ExceptionUtils.getRootCause(ex);

		if (rootCause instanceof InvalidFormatException) {
			return handleInvalidFormatException((InvalidFormatException) rootCause, headers, status, request);
		}
		if (rootCause instanceof PropertyBindingException) {
			return handlePropertyBindingException((PropertyBindingException) rootCause, headers, status, request);
		}

		ProblemType problemType = ProblemType.MENSAGEM_INCOMPREENSIVEL;
		String detail = "O corpo da requisição está inválida. Verifique erro de sintaxe.";

		Problem problem = createProblemBuilder(status, problemType, detail, MSG_ERRO_GENERICA_USUARIO_FINAL).build();
		return handleExceptionInternal(ex, problem, headers, status, request);
	}

	private ResponseEntity<Object> handleInvalidFormatException(InvalidFormatException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		ProblemType problemType = ProblemType.MENSAGEM_INCOMPREENSIVEL;
		String path = joinPath(ex.getPath());
		String detail = String.format(
				"A propriedade '%s' recebeu o valor '%s' que é de um tipo inválido. "
				+ "Corrija e informe um valor compatível com o tipo %s.",
				path, ex.getValue(), ex.getTargetType().getSimpleName());
		
		Problem problem = createProblemBuilder(status, problemType, detail, MSG_ERRO_GENERICA_USUARIO_FINAL).build();
		return handleExceptionInternal(ex, problem, headers, status, request);
	}
	
	private ResponseEntity<Object> handlePropertyBindingException(PropertyBindingException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		String path = joinPath(ex.getPath());
		ProblemType problemType = ProblemType.MENSAGEM_INCOMPREENSIVEL;
		String detail = String.format(
				"A propriedade '%s' não existe."
				+ " Corrija ou remova essa propriedade e tente novamente.", path);
		
		Problem problem = createProblemBuilder(status, problemType, detail, MSG_ERRO_GENERICA_USUARIO_FINAL).build();
		return handleExceptionInternal(ex, problem, headers, status, request);
	}
	
	@ExceptionHandler(EntidadeNaoEncontradaException.class)
	public ResponseEntity<?> handleEntidadeNaoEncontradaException(EntidadeNaoEncontradaException ex,
			WebRequest request) {
		HttpStatus status = HttpStatus.NOT_FOUND;
		ProblemType problemType = ProblemType.RECURSO_NAO_ENCONTRADO;
		String detail = ex.getMessage();

		Problem problem = createProblemBuilder(status, problemType, detail, detail).build();
		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}

	@ExceptionHandler(NegocioException.class)
	public ResponseEntity<?> handleNegocioException(NegocioException ex, WebRequest request) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		ProblemType problemType = ProblemType.ERRO_NEGOCIO;
		String detail = ex.getMessage();

		Problem problem = createProblemBuilder(status, problemType, detail, detail).build();
		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);

	}
	
	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		if (body == null) {
			body = Problem.builder()
					.status(status.value())
					.timestamp(OffsetDateTime.now())
					.title(status.getReasonPhrase())
					.userMessage(MSG_ERRO_GENERICA_USUARIO_FINAL).build();
		} else if (body instanceof String) {
			body = Problem.builder()
					.status(status.value())
					.timestamp(OffsetDateTime.now())
					.title((String) body)
					.userMessage(MSG_ERRO_GENERICA_USUARIO_FINAL).build();
		}
		return super.handleExceptionInternal(ex, body, headers, status, request);
	}
	
	private Problem.ProblemBuilder createProblemBuilder(HttpStatus status, ProblemType problemType, String detail, String userMessage) {
		return Problem.builder()
				.status(status.value())
				.timestamp(OffsetDateTime.now())
				.type(problemType.getUri())
				.title(problemType.getTitle())
				.detail(detail)
				.userMessage(userMessage);
	}
	
	private String joinPath(List<Reference> path) {
		return path.stream()
				.map(ref -> ref.getFieldName())
				.collect(Collectors.joining("."));
	}
	
}
