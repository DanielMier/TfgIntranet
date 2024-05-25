package com.registro.servicio;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.registro.repositorio.*;
import com.registro.controlador.dto.UsuarioRegistroDTO;
import com.registro.modelo.Usuario;

@Service
public class UsuarioServicioImpl implements UsuarioServicio {

    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    
    public UsuarioServicioImpl(UsuarioRepositorio usuarioRepositorio) {
        this.usuarioRepositorio = usuarioRepositorio;
    }

    @Override
    public Usuario guardar(UsuarioRegistroDTO registroDTO) {
        // Codificar la contraseña antes de guardarla en la base de datos
        String passwordEncoded = passwordEncoder.encode(registroDTO.getPassword());
        System.out.println("Contraseña codificada: " + passwordEncoded);

        // Crear una instancia de Usuario utilizando los datos del DTO
        Usuario usuario = new Usuario();
        usuario.setNombreUsuario(registroDTO.getNombreUsuario());
        usuario.setNombre(registroDTO.getNombre());
        usuario.setApellido(registroDTO.getApellido());
        usuario.setEmail(registroDTO.getEmail());
        usuario.setPassword(passwordEncoded);
        usuario.setRol(registroDTO.getRol());

        // Guardar el usuario en la base de datos y devolver el objeto guardado
        return usuarioRepositorio.save(usuario);
    }
    
    @Override
    public Usuario obtenerUsuarioPorId(Long id) {
        // Validar que el ID no sea nulo
        if (id == null) {
            throw new IllegalArgumentException("El ID no puede ser nulo");
        }
        // Buscar y devolver el usuario por ID, o devolver null si no se encuentra
        return usuarioRepositorio.findById(id).orElse(null);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Buscar al usuario por su email en la base de datos
        Usuario usuario = usuarioRepositorio.findByEmail(username);
        if(usuario == null) {
            throw new UsernameNotFoundException("Usuario o contraseña inválidos");
        }
        
        // Crear la lista de authorities basada en el rol del usuario
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(usuario.getRol()));

        // Devolver un UserDetails con el email, contraseña y authorities del usuario
        return new User(usuario.getEmail(), usuario.getPassword(), authorities);
    }
    
    public Usuario obtenerUsuarioPorNombre(String email) {
        return usuarioRepositorio.findByEmail(email);
    }
    @Override
    public Usuario autenticar(String email, String password) {
        // Buscar al usuario por su email
        Usuario usuario = usuarioRepositorio.findByEmail(email);
        
        // Verificar si se encontró un usuario y si la contraseña coincide
        if (usuario != null && passwordEncoder.matches(password, usuario.getPassword())) {
            return usuario; // Autenticación exitosa
        } else {
            return null; // Autenticación fallida
        }
    }

    @Override
    public List<Usuario> listarUsuarios() {
        // Devolver la lista de todos los usuarios en la base de datos
        return usuarioRepositorio.findAll();
    }

	@Override
	public Long obtenerIdPorEmail(String email) {
		
		Usuario usuario = usuarioRepositorio.findByEmail(email);
	    if (usuario != null) {
	        return usuario.getId();
	    } else {
	        return null; // O podrías lanzar una excepción si lo prefieres
	    }
	}
}
