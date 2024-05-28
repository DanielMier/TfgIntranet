package com.registro.controlador;

import java.beans.PropertyEditorSupport;
import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import com.registro.modelo.Evento;
import com.registro.modelo.Usuario;
import com.registro.repositorio.EventoRepositorio;
import com.registro.servicio.UsuarioServicio;

@Controller
@RequestMapping("/eventos")
public class EventoControlador {

    @Autowired
    private EventoRepositorio eventRepository;
    @Autowired
    private UsuarioServicio usuarioServicio;

    @GetMapping
    public List<Evento> getAllEvents() {
        return eventRepository.findAll();
    }
    @GetMapping("/userEvents")
    @ResponseBody
    public List<Evento> getAllEventsForLoggedUser(Principal principal) {
        String nombreUsuario = principal.getName();
        Usuario usuario = usuarioServicio.obtenerUsuarioPorNombre(nombreUsuario);
        return eventRepository.findByUsuarioId(usuario.getId());
    }


    @GetMapping("/range")
    public List<Evento> getEventsInRange(@RequestParam(value = "start") LocalDateTime start,
                                         @RequestParam(value = "end") LocalDateTime end) {
        return eventRepository.findByStartBetween(start, end);
    }

    @GetMapping("/calendario")
    public String mostrarCalendario() {
        return "calendario";
    }
    

    @GetMapping("/nuevo")
    public String showAddEventForm(Model model) {
        Evento evento = new Evento();
        // Inicializar con valores predeterminados
        evento.setStart(LocalDateTime.now());
        evento.setFinish(LocalDateTime.now().plusHours(1));
        model.addAttribute("evento", evento);
        return "newEvento";
    }


    
    @GetMapping("/allevents")
    @ResponseBody
    public List<Evento> getAllEventsForCalendar() {
        return eventRepository.findAll();
    }
    
    @PostMapping
    public String addEvent(@ModelAttribute Evento event, Principal principal) {
    	// Obtener el nombre de usuario del contexto de seguridad
        String nombreUsuario = principal.getName();

        // Obtener el objeto Usuario completo por nombre de usuario
        Usuario usuario = usuarioServicio.obtenerUsuarioPorNombre(nombreUsuario);

        // Asignar el usuario a la tarea
        event.setUsuario(usuario);
        eventRepository.save(event);
        return "redirect:/eventos/calendario";
    }

    @PatchMapping
    public Evento updateEvent(@RequestBody Evento event) {
        return eventRepository.save(event);
    }

    @DeleteMapping("/{eventId}")
    public void removeEvent(@PathVariable Long eventId) {
        eventRepository.deleteById(eventId);
    }
    
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

        binder.registerCustomEditor(LocalDateTime.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                setValue(LocalDateTime.parse(text, dateTimeFormatter));
            }

            @Override
            public String getAsText() {
                LocalDateTime value = (LocalDateTime) getValue();
                return value != null ? dateTimeFormatter.format(value) : "";
            }
        });
    }

    
}
