package com.registro.servicio;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.registro.modelo.Jugador;
import com.registro.modelo.Equipo;
import com.registro.modelo.Usuario;
import com.registro.repositorio.JugadorRepositorio;

@Service
public class JugadorServicioImpl implements JugadorServicio {

    @Autowired
    private JugadorRepositorio jugadorRepository;

    @Override
    public List<Jugador> obtenerTodosLosJugadores() {
        return jugadorRepository.findAll();
    }

    @Override
    public Jugador obtenerJugadorPorId(Long id) {
        return jugadorRepository.findById(id).orElse(null);
    }

    @Override
    public List<Jugador> obtenerJugadoresPorNombre(String nombre) {
        return jugadorRepository.findByNombre(nombre);
    }

    @Override
    public List<Jugador> obtenerJugadoresPorEquipo(Equipo equipo) {
        return jugadorRepository.findByEquipo(equipo);
    }

    @Override
    public List<Jugador> obtenerJugadoresPorUsuario(Usuario usuario) {
        return jugadorRepository.findByUsuario(usuario);
    }

    @Override
    public Jugador guardarJugador(Jugador jugador) {
        return jugadorRepository.save(jugador);
    }

    @Override
    public void eliminarJugador(Long id) {
        jugadorRepository.deleteById(id);
    }
}

