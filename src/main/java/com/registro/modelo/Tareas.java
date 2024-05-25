package com.registro.modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "tareas")
public class Tareas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "codigo", length = 255)
    private String codigo;

    @Column(name = "nombre", length = 255)
    private String nombre;

    // Relación Many-to-One con la entidad Usuario
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @Column(name = "tipo_tarea", length = 50)
    private String tipoTarea;

    @Column(name = "contenido_tecnico", columnDefinition = "TEXT")
    private String contenidoTecnico;

    @Column(name = "contenido_tactico", columnDefinition = "TEXT")
    private String contenidoTactico;

    @Column(name = "contenido_fisico", columnDefinition = "TEXT")
    private String contenidoFisico;

    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "num_jugadores")
    private String numJugadores;

    @Column(name = "duracion")
    private String duracion;

    @Column(name = "dificultad", length = 20)
    private String dificultad;

    @Column(name = "video_imagen", length = 255)
    private String videoImagen;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipoTarea() {
        return tipoTarea;
    }

    public void setTipoTarea(String tipoTarea) {
        this.tipoTarea = tipoTarea;
    }

    public String getContenidoTecnico() {
        return contenidoTecnico;
    }

    public void setContenidoTecnico(String contenidoTecnico) {
        this.contenidoTecnico = contenidoTecnico;
    }

    public String getContenidoTactico() {
        return contenidoTactico;
    }

    public void setContenidoTactico(String contenidoTactico) {
        this.contenidoTactico = contenidoTactico;
    }

    public String getContenidoFisico() {
        return contenidoFisico;
    }
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuarioId(Usuario usuario) {
        this.usuario = usuario;
    }
    public void setContenidoFisico(String contenidoFisico) {
        this.contenidoFisico = contenidoFisico;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNumJugadores() {
        return numJugadores;
    }

    public void setNumJugadores(String numJugadores) {
        this.numJugadores = numJugadores;
    }

    public String getDuracion() {
        return duracion;
    }

    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }

    public String getDificultad() {
        return dificultad;
    }

    public void setDificultad(String dificultad) {
        this.dificultad = dificultad;
    }

    public String getVideoImagen() {
        return videoImagen;
    }

    public void setVideoImagen(String videoImagen) {
        this.videoImagen = videoImagen;
    }

    public Tareas () {
    }

	public Tareas(String codigo, String nombre, String tipoTarea, String contenidoTecnico, String contenidoTactico,
			String contenidoFisico, String descripcion, String numJugadores, String duracion, String dificultad,
			String videoImagen) {
		super();
		this.codigo = codigo;
		this.nombre = nombre;
		this.tipoTarea = tipoTarea;
		this.contenidoTecnico = contenidoTecnico;
		this.contenidoTactico = contenidoTactico;
		this.contenidoFisico = contenidoFisico;
		this.descripcion = descripcion;
		this.numJugadores = numJugadores;
		this.duracion = duracion;
		this.dificultad = dificultad;
		this.videoImagen = videoImagen;
	}

	// Método isBest basado en criterios hipotéticos

    public boolean isBest(String contenidoTecnico, String contenidoTactico, String contenidoFisico) {
        return Double.parseDouble(this.dificultad) <= 3 && Double.parseDouble(this.duracion) <= 25
                && this.contenidoTecnico.equals(contenidoTecnico)
                && (this.contenidoTactico.equals(contenidoTactico) || this.contenidoFisico.equals(contenidoFisico));
    }
}