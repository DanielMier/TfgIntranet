package com.registro.modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "jugadores")
public class Jugador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "Nombre")
    private String nombre;

    @Column(name = "Dorsal")
    private Integer dorsal;

    @Column(name = "Edad")
    private Integer edad;

    @ManyToOne
    @JoinColumn(name = "equipo_id")
    private Equipo equipo;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

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

    public Integer getDorsal() {
        return dorsal;
    }

    public void setDorsal(Integer dorsal) {
        this.dorsal = dorsal;
    }

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public Equipo getEquipo() {
        return equipo;
    }

    public void setEquipo(Equipo equipo) {
        this.equipo = equipo;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Jugador(Long id, String nombre, Integer dorsal, Integer edad, Equipo equipo, Usuario usuario) {
        super();
        this.id = id;
        this.nombre = nombre;
        this.dorsal = dorsal;
        this.edad = edad;
        this.equipo = equipo;
        this.usuario = usuario;
    }

    public Jugador(String nombre, Integer dorsal, Integer edad, Equipo equipo, Usuario usuario) {
        super();
        this.nombre = nombre;
        this.dorsal = dorsal;
        this.edad = edad;
        this.equipo = equipo;
        this.usuario = usuario;
    }

    public Jugador() {
    }
}
