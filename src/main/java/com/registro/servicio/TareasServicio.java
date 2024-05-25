package com.registro.servicio;

import java.util.List;

import com.registro.modelo.Tareas;

public interface TareasServicio {
	
	public Tareas guardarTarea(Tareas tarea);
	public List<Tareas> listarTareas();
	public Tareas obtenerTareaCodigo (String codigo);
	public Tareas obtenerTareaPorId(Long id);
	List<Tareas> listarTareasPorUsuario(String nombreUsuario); 
	List<Tareas> listarTareasPorIds(List<Long> tareaIds);
	
}
