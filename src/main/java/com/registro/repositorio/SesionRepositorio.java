package com.registro.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.registro.modelo.Sesion;

@Repository
public interface SesionRepositorio extends JpaRepository<Sesion, Long> {
	// Aquí puedes agregar métodos de consulta personalizados si los necesitas
	Sesion findById(long id);

	@Query("SELECT s FROM Sesion s WHERE s.usuario.id = :usuarioId")
	List<Sesion> findByUsuarioId(@Param("usuarioId") long usuarioId);

	@Query("SELECT t.id FROM Sesion s JOIN s.tareas t WHERE s.id = :sesionId")
	List<Long> findTareaIdsBySesionId(@Param("sesionId") long sesionId);
}
