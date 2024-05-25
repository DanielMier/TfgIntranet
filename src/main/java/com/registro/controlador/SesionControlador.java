package com.registro.controlador;

import com.registro.modelo.Sesion;
import com.registro.modelo.Tareas;
import com.registro.modelo.Usuario;
import com.registro.servicio.SesionServicio;
import com.registro.servicio.TareasServicio;
import com.registro.servicio.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/sesion")
public class SesionControlador {

    private final SesionServicio sesionServicio;
    private final TareasServicio tareasServicio;
    private final UsuarioServicio usuarioServicio;

    @Autowired
    public SesionControlador(SesionServicio sesionServicio, TareasServicio tareasServicio, UsuarioServicio usuarioServicio) {
        this.sesionServicio = sesionServicio;
        this.tareasServicio = tareasServicio;
        this.usuarioServicio = usuarioServicio;
    }

    @GetMapping("/nueva")
    public String mostrarFormulario(Model model) {
        model.addAttribute("sesion", new Sesion());
        return "newSesion";
    }

    @PostMapping("/guardarForm")
    public String guardarSesionForm(@ModelAttribute Sesion sesion, Principal principal, Model model) {
    	   // Logs para depuración
        System.out.println("Nombre: " + sesion.getNombre());
        System.out.println("Fecha: " + sesion.getFecha());
        System.out.println("Número de Jugadores: " + sesion.getNumJugadores());
        System.out.println("Contenido Técnico: " + sesion.getContenidoTecnico());
        String nombreUsuario = principal.getName();
        Usuario usuario = usuarioServicio.obtenerUsuarioPorNombre(nombreUsuario);
        sesion.setUsuarioId(usuario);

        model.addAttribute("sesion", sesion);
        model.addAttribute("tareas", tareasServicio.listarTareas());
        return "listaTareas";
    }

    @PostMapping("/guardar")
    public String guardarSesion(@ModelAttribute Sesion sesion, @RequestParam List<Long> tareaIds, Principal principal,  Model model, RedirectAttributes redirectAttributes) {
        String nombreUsuario = principal.getName();
        Usuario usuario = usuarioServicio.obtenerUsuarioPorNombre(nombreUsuario);
        sesion.setUsuarioId(usuario);
        
        sesion.setTareas(tareasServicio.listarTareasPorIds(tareaIds));
        model.addAttribute("sesion", sesion);
        Sesion sesionGuardada = sesionServicio.guardarSesion(sesion);
        redirectAttributes.addAttribute("codigo", sesionGuardada.getId());
        return "redirect:/sesion/{id}";
    }

    @GetMapping("/lista")
    public String listarSesiones(Model model) {
        model.addAttribute("sesiones", sesionServicio.listarSesiones());
        return "lista-sesiones";
    }
    @GetMapping("/{id}")
    public String verDetalleSesion(@PathVariable Long id, Model model) {
        Sesion sesion = sesionServicio.obtenerSesionPorId(id);
        List<Tareas> tareas = sesionServicio.obtenerTareasPorSesionId(id);
        model.addAttribute("sesion", sesion);
        model.addAttribute("tareas", tareas);
        return "sesionDetalle";
    }
 
}
