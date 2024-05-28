package com.registro.controlador;

import com.registro.modelo.Evento;
import com.registro.modelo.Sesion;
import com.registro.modelo.Tareas;
import com.registro.modelo.Usuario;
import com.registro.repositorio.EventoRepositorio;
import com.registro.servicio.MachineLearningServicio;
import com.registro.servicio.SesionServicio;
import com.registro.servicio.TareasServicio;
import com.registro.servicio.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/sesion")
public class SesionControlador {

    private final SesionServicio sesionServicio;
    private final TareasServicio tareasServicio;
    private final UsuarioServicio usuarioServicio;
    private final EventoRepositorio eventoRepositorio;
    private final MachineLearningServicio mlService;

    @Autowired
    public SesionControlador(SesionServicio sesionServicio, TareasServicio tareasServicio, UsuarioServicio usuarioServicio, EventoRepositorio eventoRepositorio, MachineLearningServicio mlService) {
        this.sesionServicio = sesionServicio;
        this.tareasServicio = tareasServicio;
        this.usuarioServicio = usuarioServicio;
        this.eventoRepositorio = eventoRepositorio;
        this.mlService = mlService;
    }

    @GetMapping("/nueva")
    public String mostrarFormulario(Model model) {
        model.addAttribute("sesion", new Sesion());
        return "newSesion";
    }

    @PostMapping("/guardarForm")
    public String guardarSesionForm(@ModelAttribute Sesion sesion, Principal principal, Model model) {
        String nombreUsuario = principal.getName();
        Usuario usuario = usuarioServicio.obtenerUsuarioPorNombre(nombreUsuario);
        sesion.setUsuario(usuario);

        model.addAttribute("sesion", sesion);
        model.addAttribute("tareas", tareasServicio.listarTareas());
        return "listarTareasSesion";
    }

    @PostMapping("/guardar")
    public String guardarSesion(@ModelAttribute Sesion sesion, @RequestParam List<Long> tareaIds, Principal principal, Model model, RedirectAttributes redirectAttributes) {
        String nombreUsuario = principal.getName();
        Usuario usuario = usuarioServicio.obtenerUsuarioPorNombre(nombreUsuario);
        sesion.setUsuario(usuario);

        sesion.setTareas(tareasServicio.listarTareasPorIds(tareaIds));
        Sesion sesionGuardada = sesionServicio.guardarSesion(sesion);

        // Añadir evento con la sesion
        Evento evento = new Evento();
        evento.setTitle(sesion.getNombre());
        evento.setDescription(sesion.getContenidoTecnico());
        evento.setStart(sesion.getFecha());
        evento.setFinish(sesion.getFecha().plusHours(1));
        evento.setUsuario(usuario);
        eventoRepositorio.save(evento);

        redirectAttributes.addAttribute("id", sesionGuardada.getId());
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
    
    @GetMapping("/sesionRapida")
    public String mostrarFormularioConPrediccion(Model model) {
    	try {
            mlService.trainModel();
            model.addAttribute("sesion", new Sesion());
            model.addAttribute("predictionForm", new Tareas());
            return "sesionRapida";
        } catch (Exception e) {
            e.printStackTrace();
            return "/";
        }
      
    }

    @PostMapping("/predecirYGuardar")
    public String predecirYGuardarSesion(@ModelAttribute Tareas predictionForm, @ModelAttribute Sesion sesion, Principal principal, Model model, RedirectAttributes redirectAttributes) {
    	
        try {
            String nombreUsuario = principal.getName();
            Usuario usuario = usuarioServicio.obtenerUsuarioPorNombre(nombreUsuario);
            sesion.setUsuario(usuario);

            List<Tareas> predictedExercises = mlService.predictBestExercises(
                predictionForm.getContenidoFisico(),
                predictionForm.getContenidoTecnico(),
                predictionForm.getContenidoTactico(),
                predictionForm.getNumJugadores()
            );

            if (predictedExercises.isEmpty()) {
                model.addAttribute("error", "No se encontraron ejercicios predichos.");
                return "newSesionWithPrediction";
            }

            sesion.setTareas(predictedExercises);
            Sesion sesionGuardada = sesionServicio.guardarSesion(sesion);

            // Añadir evento con la sesion
            Evento evento = new Evento();
            evento.setTitle(sesion.getNombre());
            evento.setDescription(sesion.getContenidoTecnico());
            evento.setStart(sesion.getFecha());
            evento.setFinish(sesion.getFecha().plusHours(1));
            evento.setUsuario(usuario);
            eventoRepositorio.save(evento);

            redirectAttributes.addAttribute("id", sesionGuardada.getId());
            return "redirect:/sesion/{id}";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Ocurrió un error al predecir y guardar la sesión.");
            return "newSesionWithPrediction";
        }
    }
}
