package com.registro.repositorio;

import org.springframework.data.repository.CrudRepository;

import com.registro.modelo.Convidado;
import com.registro.modelo.Evento;

public interface ConvidadoRepositorio extends CrudRepository<Convidado, Long> {
	Iterable<Convidado> findByEvento(Evento evento);
}