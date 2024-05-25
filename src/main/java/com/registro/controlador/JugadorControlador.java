package com.registro.controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.registro.modelo.Equipo;
import com.registro.modelo.Jugador;
import com.registro.modelo.Usuario;
import com.registro.servicio.EquipoServicio;
import com.registro.servicio.JugadorServicio;
import com.registro.servicio.UsuarioServicio;

@Controller
@RequestMapping("/jugadores")
public class JugadorControlador {

    @Autowired
    private JugadorServicio jugadorService;
    @Autowired
    private EquipoServicio equipoService;
    @Autowired
    private UsuarioServicio usuarioService;

    @GetMapping
    public List<Jugador> obtenerTodosLosJugadores() {
        return jugadorService.obtenerTodosLosJugadores();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Jugador> obtenerJugadorPorId(@PathVariable Long id) {
        Jugador jugador = jugadorService.obtenerJugadorPorId(id);
        return jugador != null ? ResponseEntity.ok(jugador) : ResponseEntity.notFound().build();
    }

    @GetMapping("/nombre/{nombre}")
    public List<Jugador> obtenerJugadoresPorNombre(@PathVariable String nombre) {
        return jugadorService.obtenerJugadoresPorNombre(nombre);
    }

    @GetMapping("/equipo/{equipoId}")
    public List<Jugador> obtenerJugadoresPorEquipo(@PathVariable Long equipoId) {
        Equipo equipo = new Equipo();
        equipo.setId(equipoId);
        return jugadorService.obtenerJugadoresPorEquipo(equipo);
    }

    @GetMapping("/usuario/{usuarioId}")
    public List<Jugador> obtenerJugadoresPorUsuario(@PathVariable Long usuarioId) {
        Usuario usuario = new Usuario();
        usuario.setId(usuarioId);
        return jugadorService.obtenerJugadoresPorUsuario(usuario);
    }

    @PostMapping("/{equipoId}")
    public String guardarJugador(@PathVariable Long equipoId, @ModelAttribute Jugador jugador, @AuthenticationPrincipal UserDetails principal) {
        // Obtener el equipo por el ID
        Equipo equipo = equipoService.obtenerEquipoPorId(equipoId);
        
        // Obtener el nombre de usuario del contexto de seguridad
        String nombreUsuario = principal.getUsername();
        
        // Obtener el objeto Usuario completo por nombre de usuario
        Usuario usuario = usuarioService.obtenerUsuarioPorNombre(nombreUsuario);
        
        // Asignar el equipo y el usuario al jugador
        jugador.setEquipo(equipo);
        jugador.setUsuario(usuario);
        
        // Guardar el jugador
        jugadorService.guardarJugador(jugador);
        
        return "redirect:/";
    }

    @GetMapping("/nuevo")
    public String mostrarFormulario(@RequestParam(name = "id", required = false) Long equipoId, Model model) {
        // Verificar si se proporcionó un equipoId en la URL
        if (equipoId != null) {
            // Obtener el equipo por el ID
            Equipo equipo = equipoService.obtenerEquipoPorId(equipoId);
            
            // Verificar si se encontró el equipo
            if (equipo != null) {
                // Agregar el equipo al modelo
                model.addAttribute("equipo", equipo);
            } else {
                // Si no se encuentra el equipo, redirigir a una página de error o manejarlo de alguna otra manera
                return "redirect:/error";
            }
        }

        // Agregar un nuevo jugador al modelo
        model.addAttribute("jugador", new Jugador());
        
        // Retornar el nombre de la vista
        return "forumlarioJugadores"; // Esto asume que tienes una vista llamada formularioJugadores
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarJugador(@PathVariable Long id) {
        jugadorService.eliminarJugador(id);
        return ResponseEntity.noContent().build();
    }
}
