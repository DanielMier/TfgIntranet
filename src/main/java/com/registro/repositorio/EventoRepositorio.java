package com.registro.repositorio;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.registro.modelo.Evento;

@Repository
public interface EventoRepositorio extends JpaRepository<Evento, Long> {
    List<Evento> findByStartBetween(LocalDateTime startDate, LocalDateTime endDate);
}
