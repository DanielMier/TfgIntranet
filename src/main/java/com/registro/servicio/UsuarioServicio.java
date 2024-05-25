package com.registro.servicio;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.registro.controlador.dto.UsuarioRegistroDTO;
import com.registro.modelo.Usuario;

public interface UsuarioServicio extends UserDetailsService {

	public Usuario guardar(UsuarioRegistroDTO registroDTO);

	public List<Usuario> listarUsuarios();

	public Usuario obtenerUsuarioPorId(Long id);

	public Usuario autenticar(String email, String password);

	public Usuario obtenerUsuarioPorNombre(String nombre);
	
	public Long obtenerIdPorEmail(String email);
}
