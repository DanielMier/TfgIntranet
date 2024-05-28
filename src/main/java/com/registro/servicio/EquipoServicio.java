package com.registro.servicio;

import com.registro.modelo.Equipo;
import com.registro.modelo.Usuario;

import java.util.List;

public interface EquipoServicio {
	List<Equipo> buscarPorNombreEquipo(String nombreEquipo);
    List<Equipo> buscarPorIdUsuario(Long idUsuario);
    Equipo buscarPorIdYIdUsuario(Long idEquipo, Long idUsuario);
	Equipo guardarEquipo(Equipo equipo);
	Equipo obtenerEquipoPorId (Long Id);
	List<Equipo> listarPorUsuario(Usuario usuario);
 
}
