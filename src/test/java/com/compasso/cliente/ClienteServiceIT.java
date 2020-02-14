package com.compasso.cliente;

import static io.restassured.RestAssured.basePath;
import static io.restassured.RestAssured.enableLoggingOfRequestAndResponseIfValidationFails;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.port;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItem;

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
 * Classe de Teste de API de Clientes
 * @author yurifws
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
public class ClienteServiceIT {
	
	private static final int CLIENTE_ID_INEXISTENTE = 100;

	@LocalServerPort
	private int localServerPort;
	
	@Autowired
	private DatabaseCleaner databaseCleaner;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	private String jsonCorretoCliente;
	private Cliente clienteJorge;
	private Cidade cidadeNatal;
	private Estado estadoRioGrandeNorte;

	@Before
	public void setUp() {
		enableLoggingOfRequestAndResponseIfValidationFails();
		basePath = "/clientes";
		port = localServerPort;
		
		jsonCorretoCliente = ResourceUtils
				.getContentFromResource("/json/correto/cliente-joao.json");
		
		databaseCleaner.clearTables();
		preparaDados();
		
	}
	
	@Test
	public void shouldRetornarStatus200_WhenConsultarClientes() {
		given()
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.statusCode(HttpStatus.OK.value());
	}
	
	@Test
	public void shouldRetornarRespostaEStatus201_WhenCadastrarCliente() {
		given()
			.body(jsonCorretoCliente)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.CREATED.value())
			.body("nome", equalTo("Joao Nunes da Silva"))
			.body("sexo", equalTo("M"))
			.body("dataNascimento", equalTo("1994-11-15"))
			.body("idade", equalTo(23))
			.body("cidade.id", equalTo(1));
	}
	
	@Test
	public void shouldRetornarRespostaEStatusCorretos_WhenConsultarClienteExistente() {
		clienteJorge.toString();
		given()
			.pathParam("clienteId", clienteJorge.getId())
			.accept(ContentType.JSON)
		.when()
			.get("/{clienteId}")
		.then()
			.statusCode(HttpStatus.OK.value())
			.body("nome", equalTo(clienteJorge.getNome()))
			.body("sexo", equalTo(String.valueOf(clienteJorge.getSexo())))
			//.body("dataNascimento", equalTo(clienteJorge.getDataNascimento()))
			.body("idade", equalTo(clienteJorge.getIdade()))
			.body("cidade.nome", equalTo(clienteJorge.getCidade().getNome()))
			.body("cidade.estado.nome", equalTo(clienteJorge.getCidade().getEstado().getNome()));
	}
	
	@Test
	public void shouldRetornarRespostaEStatusCorretos_WhenConsultarClientePorNomeExistente() {
		given()
			.queryParam("nome", clienteJorge.getNome())
			.accept(ContentType.JSON)
		.when()
			.get("/por-nome")
		.then()
			.statusCode(HttpStatus.OK.value())
			.body("nome", hasItem(clienteJorge.getNome()))
			.body("sexo", hasItem(String.valueOf(clienteJorge.getSexo())))
			//.body("dataNascimento", hasItem(clienteJorge.getDataNascimento()))
			.body("idade", hasItem(clienteJorge.getIdade()))
			.body("cidade.nome", hasItem(clienteJorge.getCidade().getNome()))
			.body("cidade.estado.nome", hasItem(clienteJorge.getCidade().getEstado().getNome()));
	}
	
	@Test
	public void shouldRetornarStatus404_WhenConsultarClienteInexistente() {
		given()
			.pathParam("clienteId", CLIENTE_ID_INEXISTENTE)
			.accept(ContentType.JSON)
		.when()
			.get("/{clienteId}")
		.then()
			.statusCode(HttpStatus.NOT_FOUND.value());
	}
	
	@Test
	public void shouldRetornarStatus400_WhenCadastrarClienteNomeNull() {
		String jsonClienteNomeNull = ResourceUtils
				.getContentFromResource("/json/incorreto/cliente-nome-null.json");
		given()
			.body(jsonClienteNomeNull)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.BAD_REQUEST.value());
	}
	
	@Test
	public void shouldRetornarStatus400_WhenCadastrarClienteSemSexo() {
		String jsonClienteSemCidade = ResourceUtils
				.getContentFromResource("/json/incorreto/cliente-leonardo-sem-sexo.json");
		given()
			.body(jsonClienteSemCidade)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.BAD_REQUEST.value());
	}
	
	@Test
	public void shouldRetornarStatus400_WhenCadastrarClienteSemDataNascimento() {
		String jsonClienteSemCidade = ResourceUtils
				.getContentFromResource("/json/incorreto/cliente-leonardo-sem-data-nascimento.json");
		given()
			.body(jsonClienteSemCidade)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.BAD_REQUEST.value());
	}
	
	@Test
	public void shouldRetornarStatus400_WhenCadastrarClienteComDataNascimentoInvalida() {
		String jsonClienteSemCidade = ResourceUtils
				.getContentFromResource("/json/incorreto/cliente-leonardo-com-data-nascimento-invalida.json");
		given()
			.body(jsonClienteSemCidade)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.BAD_REQUEST.value());
	}
	
	@Test
	public void shouldRetornarStatus400_WhenCadastrarClienteSemCidade() {
		String jsonClienteSemCidade = ResourceUtils
				.getContentFromResource("/json/incorreto/cliente-leonardo-sem-cidade.json");
		given()
			.body(jsonClienteSemCidade)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.BAD_REQUEST.value());
	}
	
	@Test
	public void shouldRetornarStatus400_WhenCadastrarClienteSemCidadeId() {
		String jsonClienteSemCidadeId = ResourceUtils
				.getContentFromResource("/json/incorreto/cliente-leonardo-sem-codigo-cidade.json");
		given()
			.body(jsonClienteSemCidadeId)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.BAD_REQUEST.value());
	}
	
	@Test
	public void shouldRetornarStatus204_WhenDeletarCliente() {
		given()
			.pathParam("cidadeId", clienteJorge.getId())
			.accept(ContentType.JSON)
		.when()
			.delete("/{cidadeId}")
		.then()
			.statusCode(HttpStatus.NO_CONTENT.value());
	}
	
	private void preparaDados() {
		estadoRioGrandeNorte = new Estado();
		estadoRioGrandeNorte.setNome("Rio Grande do Norte");
		estadoRioGrandeNorte = estadoRepository.save(estadoRioGrandeNorte);
		cidadeNatal = new Cidade();
		cidadeNatal.setNome("Natal");
		cidadeNatal.setEstado(estadoRioGrandeNorte);
		cidadeNatal = cidadeRepository.save(cidadeNatal);
		
		clienteJorge = new Cliente();
		clienteJorge.setNome("Jorge Fernandes Domingos");
		clienteJorge.setSexo('M');
		clienteJorge.setDataNascimento(LocalDate.of(1994, 01, 11));
		clienteJorge.obterIdade();
		clienteJorge.setCidade(cidadeNatal);
		clienteRepository.save(clienteJorge);
		
	}


}
