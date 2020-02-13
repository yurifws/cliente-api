package com.compasso.cliente;

import static io.restassured.RestAssured.basePath;
import static io.restassured.RestAssured.enableLoggingOfRequestAndResponseIfValidationFails;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.port;
import static org.hamcrest.Matchers.equalTo;

import java.time.LocalDate;

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
import com.compasso.cliente.domain.model.Cliente;
import com.compasso.cliente.domain.model.Estado;
import com.compasso.cliente.domain.repository.CidadeRepository;
import com.compasso.cliente.domain.repository.ClienteRepository;
import com.compasso.cliente.domain.repository.EstadoRepository;
import com.compasso.cliente.util.DatabaseCleaner;
import com.compasso.cliente.util.ResourceUtils;

import io.restassured.http.ContentType;
/**
 * Classe de Teste de API de Cidades
 * @author yurifws
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
public class CidadeServiceIT {
	
	private static final int CIDADE_ID_INEXISTENTE = 100;

	@LocalServerPort
	private int localServerPort;
	
	@Autowired
	private DatabaseCleaner databaseCleaner;
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	private String jsonCorretoCidade;
	private Cidade cidadeNatal;
	private Estado estadoPernambuco;
	
	private Cliente cliente;

	@Before
	public void setUp() {
		enableLoggingOfRequestAndResponseIfValidationFails();
		basePath = "/cidades";
		port = localServerPort;
		
		jsonCorretoCidade = ResourceUtils
				.getContentFromResource("/json/correto/cidade-recife.json");
		
		databaseCleaner.clearTables();
		preparaDados();
		
	}
	
	@Test
	public void shouldRetornarStatus200_WhenConsultarCidades() {
		given()
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.statusCode(HttpStatus.OK.value());
	}
	
	@Test
	public void shouldRetornarStatus201_WhenCadastrarCidade() {
		given()
			.body(jsonCorretoCidade)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.CREATED.value());
	}
	
	@Test
	public void shouldRetornarRespostaEStatusCorretos_WhenConsultarCidadeExistente() {
		given()
			.pathParam("cidadeId", cidadeNatal.getId())
			.accept(ContentType.JSON)
		.when()
			.get("/{cidadeId}")
		.then()
			.statusCode(HttpStatus.OK.value())
			.body("nome", equalTo(cidadeNatal.getNome()));
	}
	
	@Test
	public void shouldRetornarStatus404_WhenConsultarCidadeInexistente() {
		given()
			.pathParam("cidadeId", CIDADE_ID_INEXISTENTE)
			.accept(ContentType.JSON)
		.when()
			.get("/{cidadeId}")
		.then()
			.statusCode(HttpStatus.NOT_FOUND.value());
	}
	
	@Test
	public void shouldRetornarStatus400_WhenCadastrarCidadeNomeNull() {
		String jsonCidadeNomeNull = ResourceUtils
				.getContentFromResource("/json/correto/cidade-nome-null.json");
		given()
			.body(jsonCidadeNomeNull)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.BAD_REQUEST.value());
	}
	
	@Test
	public void shouldRetornarStatus400_WhenCadastrarCidadeSemEstadoId() {
		String jsonCidadeSemEstadoId = ResourceUtils
				.getContentFromResource("/json/correto/cidade-recife-sem-codigo-estado.json");
		given()
			.body(jsonCidadeSemEstadoId)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.BAD_REQUEST.value());
	}
	
	@Test
	public void shouldRetornarStatus400_WhenCadastrarCidadeSemEstado() {
		String jsonCidadeSemEstado = ResourceUtils
				.getContentFromResource("/json/correto/cidade-recife-sem-estado.json");
		given()
			.body(jsonCidadeSemEstado)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.BAD_REQUEST.value());
	}
	
	@Test
	public void shouldRetornarStatus400_WhenDeletarCidadeEmUso() {
		given()
			.pathParam("cidadeId", cidadeNatal.getId())
			.accept(ContentType.JSON)
		.when()
			.delete("/{cidadeId}")
		.then()
			.statusCode(HttpStatus.BAD_REQUEST.value());
	}
	
	private void preparaDados() {
		estadoPernambuco = new Estado();
		estadoPernambuco.setNome("Pernambuco");
		estadoPernambuco = estadoRepository.save(estadoPernambuco);
		
		Estado estadoRioGrandeNorte = new Estado();
		estadoRioGrandeNorte.setNome("Rio Grande do Norte");
		estadoRioGrandeNorte = estadoRepository.save(estadoRioGrandeNorte);
		cidadeNatal = new Cidade();
		cidadeNatal.setNome("Natal");
		cidadeNatal.setEstado(estadoRioGrandeNorte);
		cidadeNatal = cidadeRepository.save(cidadeNatal);
		
		cliente = new Cliente();
		cliente.setNome("Rafael Júnior");
		cliente.setSexo('M');
		cliente.setDataNascimento(LocalDate.of(1994, 11, 21));
		cliente.obterIdade();
		cliente.setCidade(cidadeNatal);
		clienteRepository.save(cliente);
		
	}


}
