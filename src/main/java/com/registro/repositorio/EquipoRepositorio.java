package com.registro.repositorio;

import com.registro.modelo.Equipo;
import com.registro.modelo.Usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface EquipoRepositorio extends JpaRepository<Equipo, Long> {
	List<Equipo> findByNombreEquipo(String nombreEquipo);

	List<Equipo> findByEntrenadorId(Long idUsuario);
	
	List<Equipo> findByEntrenador(Usuario usuario);
	Equipo findByIdAndEntrenadorId(Long idEquipo, Long idUsuario);

}
