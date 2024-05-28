package com.registro.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.registro.modelo.Tareas;
import com.registro.modelo.Usuario;

@Repository
public interface TareasRepositorio extends JpaRepository<Tareas, Long> {

	/**
	 * Busca una tarea por su código.
	 * 
	 * @param codigo El código de la tarea a buscar.
	 * @return La tarea encontrada, o null si no existe.
	 */
	Tareas findByCodigo(String codigo);

	List<Tareas> findByUsuario(Usuario usuario);
	
	// ML search
	@Query("SELECT t FROM Tareas t WHERE t.contenidoTecnico = :contenidoTecnico AND t.contenidoTactico = :contenidoTactico AND t.contenidoFisico = :contenidoFisico ORDER BY t.dificultad ASC, t.duracion DESC")
	List<Tareas> findTop5(
			@Param("contenidoTecnico") String contenidoTecnico,
			@Param("contenidoTactico") String contenidoTactico, 
			@Param("contenidoFisico") String contenidoFisico
			);
	
	@Query("SELECT t FROM Tareas t WHERE t.contenidoTecnico = :contenidoTecnico OR t.contenidoTactico = :contenidoTactico OR t.contenidoFisico = :contenidoFisico ORDER BY t.dificultad ASC, t.duracion DESC")
	List<Tareas> findRelatedTop5(
			@Param("contenidoTecnico") String contenidoTecnico,
			@Param("contenidoTactico") String contenidoTactico, 
			@Param("contenidoFisico") String contenidoFisico
			);

}