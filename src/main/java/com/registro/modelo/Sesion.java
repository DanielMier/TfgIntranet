package com.registro.modelo;

import javax.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "sesion")
public class Sesion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime fecha;

    private int numJugadores;

    private String contenidoTecnico;

    @ManyToMany
    @JoinTable(
        name = "sesion_tareas",
        joinColumns = @JoinColumn(name = "sesion_id"),
        inverseJoinColumns = @JoinColumn(name = "tarea_id")
    )
    private List<Tareas> tareas;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public int getNumJugadores() {
        return numJugadores;
    }

    public void setNumJugadores(int numJugadores) {
        this.numJugadores = numJugadores;
    }

    public String getContenidoTecnico() {
        return contenidoTecnico;
    }

    public void setContenidoTecnico(String contenidoTecnico) {
        this.contenidoTecnico = contenidoTecnico;
    }
    
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<Tareas> getTareas() {
        return tareas;
    }

    public void setTareas(List<Tareas> tareas) {
        this.tareas = tareas;
    }
}
