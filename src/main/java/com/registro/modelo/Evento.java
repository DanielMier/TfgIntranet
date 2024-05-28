package com.registro.modelo;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "event")
public class Evento implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "title")
	private String title;
	@Column(name = "description")
	private String description;
	@Column(name = "start")
	private LocalDateTime start;
	@Column(name = "finish")
	private LocalDateTime finish;
	
	 // Relación Many-to-One con la entidad Usuario
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
    
	public Evento() {
        super();
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDateTime getStart() {
		return start;
	}

	public void setStart(LocalDateTime start) {
		this.start = start;
	}

	public LocalDateTime getFinish() {
		return finish;
	}

	public void setFinish(LocalDateTime finish) {
		this.finish = finish;
	}
	
	 public Usuario getUsuario() {
	        return usuario;
	    }

	    public void setUsuario(Usuario usuario) {
	        this.usuario = usuario;
	    }

	@Override
	public String toString() {
		return "Event [id=" + id + ", title=" + title + ", description=" + description + ", start=" + start
				+ ", finish=" + finish + "]";
	}
}
