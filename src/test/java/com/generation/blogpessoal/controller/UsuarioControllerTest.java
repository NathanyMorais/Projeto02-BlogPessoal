package com.generation.blogpessoal.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;

import com.generation.blogpessoal.model.Usuario;
import com.generation.blogpessoal.repository.UsuarioRepository;
import com.generation.blogpessoal.service.UsuarioService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT) //determina que a classe deriva do spring boot test (caso a porta 8080 esteja ocupada, o spring vai definir outra porta automaticamente)
@TestInstance(TestInstance.Lifecycle.PER_CLASS) 
public class UsuarioControllerTest {
	
	@Autowired
	private TestRestTemplate testRestTemplate;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@BeforeAll
	void start() {
		usuarioRepository.deleteAll();
		
		usuarioService.cadastrarUsuario(new Usuario (0L, "Root", "root@root.com","rootroot"," ", null));
	}
	
	@Test
	@DisplayName("Vou cadastrar um usuário")
	public void deveCriarUmUsuario() {
		
		HttpEntity<Usuario> corpoRequisicao = new HttpEntity<Usuario> (new Usuario(0L, "João da Silva", "jds@email.com.br","12345678","-",null));
		
		ResponseEntity<Usuario> corpoResposta = testRestTemplate
				.exchange("/usuarios/cadastrar",HttpMethod.POST,corpoRequisicao,Usuario.class);
		assertEquals(HttpStatus.CREATED,corpoResposta.getStatusCode());
	}
	
	@Test
	@DisplayName("Verificar a duplicação do usuário")
	public void naoDuplicarUsuario() {
		
		usuarioService.cadastrarUsuario(new Usuario(0L, "Maria Aparecida", "mp@email.com.br", "87654321", "-", null)); 
		HttpEntity<Usuario> corpoRequisicao = new HttpEntity<Usuario> (new Usuario(0L, "Maria Aparecida", "mp@email.com.br", "87654321", "-", null));
		
		ResponseEntity<Usuario> corpoResposta = testRestTemplate
				.exchange("/usuarios/cadastrar",HttpMethod.POST,corpoRequisicao,Usuario.class);
		assertEquals(HttpStatus.BAD_REQUEST,corpoResposta.getStatusCode());
	}
	
	@Test
	@DisplayName("Atualizar um Usuário")
	public void atualizarUmUsuario () {
		Optional <Usuario> usuarioCadastrado = usuarioService.cadastrarUsuario(new Usuario(0L, "Carlos Roberto", "cr@email.com.br", "12365478", 
				"-", null));
		Usuario usuarioUpdate = new Usuario (usuarioCadastrado.get().getId(),"Carlos Roberto Pereira", "crp@email.com.br", "12365478", 
				"-", null);
		HttpEntity<Usuario> corpoRequisicao = new HttpEntity<Usuario>(usuarioUpdate);
		
		ResponseEntity<Usuario> corpoResposta = testRestTemplate
				.withBasicAuth("root@root.com", "rootroot")
				.exchange("/usuarios/atualizar", HttpMethod.PUT, corpoRequisicao, Usuario.class);
		
		assertEquals(HttpStatus.OK, corpoResposta.getStatusCode());
	}
	
	@Test
	@DisplayName("Listar todos os Usuários")
	public void mostrarTodosOsUsuarios() {
		
		usuarioService.cadastrarUsuario(new Usuario(0L, "Mariana Barbosa", "mb@email.com.br", "mb165478", 
				"-", null));
		
		usuarioService.cadastrarUsuario(new Usuario(0L, "Ricardo Pereira", "rprp@email.com.br", "rps165478", 
				"-", null));
		
		ResponseEntity<String> resposta = testRestTemplate
				.withBasicAuth("root@root.com", "rootroot")
				.exchange("/usuarios", HttpMethod.GET, null, String.class);
		
		assertEquals(HttpStatus.OK, resposta.getStatusCode());
	}

}
