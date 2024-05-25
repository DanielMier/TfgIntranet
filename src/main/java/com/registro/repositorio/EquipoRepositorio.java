package com.registro.repositorio;

import com.registro.modelo.Equipo;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface EquipoRepositorio extends JpaRepository<Equipo, Long> {
	List<Equipo> findByNombreEquipo(String nombreEquipo);

	List<Equipo> findByEntrenadorId(Long idUsuario);

	Equipo findByIdAndEntrenadorId(Long idEquipo, Long idUsuario);

}
