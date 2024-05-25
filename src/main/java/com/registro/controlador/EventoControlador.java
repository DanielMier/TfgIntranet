package com.registro.controlador;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.registro.modelo.Evento;
import com.registro.repositorio.EventoRepositorio;

@Controller
@RequestMapping("/eventos")
public class EventoControlador {

    @Autowired
    private EventoRepositorio eventRepository;

    @GetMapping
    public List<Evento> getAllEvents() {
        return eventRepository.findAll();
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
    
    @GetMapping("/allevents")
    @ResponseBody
    public List<Evento> getAllEventsForCalendar() {
        return eventRepository.findAll();
    }
    
    @PostMapping
    public Evento addEvent(@RequestBody Evento event) {
        return eventRepository.save(event);
    }

    @PatchMapping
    public Evento updateEvent(@RequestBody Evento event) {
        return eventRepository.save(event);
    }

    @DeleteMapping("/{eventId}")
    public void removeEvent(@PathVariable Long eventId) {
        eventRepository.deleteById(eventId);
    }
}
