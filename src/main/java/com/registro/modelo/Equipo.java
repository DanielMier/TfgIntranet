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
@Table(name = "equipos")
public class Equipo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "Nombre_del_equipo")
    private String nombreEquipo;

    @Column(name = "Categoria")
    private String categoria;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario entrenador;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreEquipo() {
        return nombreEquipo;
    }

    public void setNombreEquipo(String nombreEquipo) {
        this.nombreEquipo = nombreEquipo;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Usuario getEntrenador() {
        return entrenador;
    }

    public void setEntrenador(Usuario entrenador) {
        this.entrenador = entrenador;
    }

    public Equipo(Long id, String nombreEquipo, String categoria, Usuario entrenador) {
        super();
        this.id = id;
        this.nombreEquipo = nombreEquipo;
        this.categoria = categoria;
        this.entrenador = entrenador;
    }

    public Equipo(String nombreEquipo, String categoria, Usuario entrenador) {
        super();
        this.nombreEquipo = nombreEquipo;
        this.categoria = categoria;
        this.entrenador = entrenador;
    }

    public Equipo() {
    }
}
