package com.registro.servicio;

import java.util.List;
import com.registro.modelo.Jugador;
import com.registro.modelo.Equipo;
import com.registro.modelo.Usuario;

public interface JugadorServicio {

    List<Jugador> obtenerTodosLosJugadores();

    Jugador obtenerJugadorPorId(Long id);

    List<Jugador> obtenerJugadoresPorNombre(String nombre);

    List<Jugador> obtenerJugadoresPorEquipo(Equipo equipo);

    List<Jugador> obtenerJugadoresPorUsuario(Usuario usuario);

    Jugador guardarJugador(Jugador jugador);

    void eliminarJugador(Long id);
}
