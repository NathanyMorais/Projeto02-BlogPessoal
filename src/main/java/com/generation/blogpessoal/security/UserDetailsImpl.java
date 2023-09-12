package com.generation.blogpessoal.security;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.generation.blogpessoal.model.Usuario;

public class UserDetailsImpl implements UserDetails{
	/*A Classe UserDetailsImpl implementa a Interface UserDetails, que tem como principal funcionalidade fornecer as informações básicas do usuário 
	para o Spring Security (Usuário, Senha, Direitos de acesso e as Restrições da conta).*/
	
	private static final long serialVersionUID = 1L;
	
	private String userName;
	private String passWord;
	private List<GrantedAuthority> authorities;
	
	public UserDetailsImpl(Usuario user) { // Método Construtor da Classe UserDetailsImpl, com os atributos username e password
		this.userName = user.getUsuario();
		this.passWord = user.getSenha();
	}
	
	public UserDetailsImpl() { } //Método Construtor da Classe UserDetailsImpl vazio que será utilizado eventualmente para gerar Objetos com os atributos não preenchidos.

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() { //método responsável por retornar os Direitos de Acesso do Usuário (Autorizações ou Roles).
		return authorities; //Como não iremos implementar os Direitos de Acesso do Usuário, o Método sempre retornará uma Collection vazia
	}

	@Override
	public String getPassword() {
		return passWord;
	}

	@Override
	public String getUsername() {
		return userName;
	}
	
	/*Para simplificar a nossa implementação da Spring Security, todos os métodos de Propriedade do Usuário irão retornar o valor true. 
	  Assim qualquer usuário conseguirá autenticar o sistema*/
	
	@Override
	public boolean isAccountNonExpired() {//Indica se o acesso do usuário expirou (tempo de acesso). Uma conta expirada não pode ser autenticada (return false).
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {//Indica se o usuário está bloqueado ou desbloqueado. Um usuário bloqueado não pode ser autenticado (return false).
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {//Indica se as credenciais do usuário (senha) expiraram (precisa ser trocada). Senha expirada impede a autenticação (return false).
		return true;
	}

	@Override
	public boolean isEnabled() {//Indica se o usuário está habilitado ou desabilitado. Um usuário desabilitado não pode ser autenticado (return false).
		return true;
	}
	

}
