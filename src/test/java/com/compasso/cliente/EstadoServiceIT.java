package com.compasso.cliente;

import static io.restassured.RestAssured.basePath;
import static io.restassured.RestAssured.enableLoggingOfRequestAndResponseIfValidationFails;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.port;
import static org.hamcrest.Matchers.equalTo;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.compasso.cliente.domain.model.Cidade;
import com.compasso.cliente.domain.model.Estado;
import com.compasso.cliente.domain.repository.CidadeRepository;
import com.compasso.cliente.domain.repository.EstadoRepository;
import com.compasso.cliente.util.DatabaseCleaner;
import com.compasso.cliente.util.ResourceUtils;

import io.restassured.http.ContentType;
/**
 * Classe de Teste de API de Estados
 * @author yurifws
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
public class EstadoServiceIT {
	
	private static final int ESTADO_ID_INEXISTENTE = 100;

	@LocalServerPort
	private int localServerPort;
	
	@Autowired
	private DatabaseCleaner databaseCleaner;
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	private String jsonCorretoEstado;
	private Estado estadoSaoPaulo;
	private Cidade cidadeSaoPaulo;

	@Before
	public void setUp() {
		enableLoggingOfRequestAndResponseIfValidationFails();
		basePath = "/estados";
		port = localServerPort;
		
		jsonCorretoEstado = ResourceUtils
				.getContentFromResource("/json/correto/estado-pernambuco.json");
		
		databaseCleaner.clearTables();
		preparaDados();
		
	}
	
	@Test
	public void shouldRetornarStatus200_WhenConsultarEstados() {
		given()
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.statusCode(HttpStatus.OK.value());
	}
	
	@Test
	public void shouldRetornarStatus201_WhenCadastrarEstado() {
		given()
			.body(jsonCorretoEstado)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.CREATED.value());
	}
	
	@Test
	public void shouldRetornarRespostaEStatusCorretos_WhenConsultarEstadoExistente() {
		given()
			.pathParam("estadoId", estadoSaoPaulo.getId())
			.accept(ContentType.JSON)
		.when()
			.get("/{estadoId}")
		.then()
			.statusCode(HttpStatus.OK.value())
			.body("nome", equalTo(estadoSaoPaulo.getNome()));
	}
	
	@Test
	public void shouldRetornarStatus404_WhenConsultarEstadoInexistente() {
		given()
			.pathParam("estadoId", ESTADO_ID_INEXISTENTE)
			.accept(ContentType.JSON)
		.when()
			.get("/{estadoId}")
		.then()
			.statusCode(HttpStatus.NOT_FOUND.value());
	}
	
	@Test
	public void shouldRetornarRespostaEStatusCorretos_WhenDeletarEstadoEmUso() {
		given()
			.pathParam("estadoId", estadoSaoPaulo.getId())
			.accept(ContentType.JSON)
		.when()
			.delete("/{estadoId}")
		.then()
			.statusCode(HttpStatus.BAD_REQUEST.value());
	}
	
	@Test
	public void shouldRetornarStatus400_WhenCadastrarEstadoSemNome() {
		String jsonEstadoSemNome = ResourceUtils
				.getContentFromResource("/json/correto/estado-sem-nome.json");
		given()
			.body(jsonEstadoSemNome)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.BAD_REQUEST.value());
	}
	
	private void preparaDados() {
		estadoSaoPaulo = new Estado();
		estadoSaoPaulo.setNome("Sao Paulo");
		estadoSaoPaulo = estadoRepository.save(estadoSaoPaulo);
		cidadeSaoPaulo = new Cidade();
		cidadeSaoPaulo.setNome("Sao Paulo");
		cidadeSaoPaulo.setEstado(estadoSaoPaulo);
		cidadeRepository.save(cidadeSaoPaulo);
	}


}
