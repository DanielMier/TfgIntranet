package com.registro.repositorio;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.registro.modelo.Jugador;
import com.registro.modelo.Equipo;
import com.registro.modelo.Usuario;

@Repository
public interface JugadorRepositorio extends JpaRepository<Jugador, Long> {

    // Buscar jugadores por nombre
    List<Jugador> findByNombre(String nombre);

    // Buscar jugadores por equipo
    List<Jugador> findByEquipo(Equipo equipo);

    // Buscar jugadores por usuario
    List<Jugador> findByUsuario(Usuario usuario);
}

