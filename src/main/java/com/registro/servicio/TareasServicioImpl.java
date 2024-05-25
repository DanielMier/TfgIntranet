package com.registro.servicio;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.registro.modelo.Tareas;
import com.registro.modelo.Usuario;
import com.registro.repositorio.TareasRepositorio;

@Service
public class TareasServicioImpl implements TareasServicio {

	private TareasRepositorio tareasRepositorio;
	private UsuarioServicio usuarioServicio;

	@Autowired
	public TareasServicioImpl(TareasRepositorio tareasRepositorio, UsuarioServicio usuarioServicio) {
		this.tareasRepositorio = tareasRepositorio;
		this.usuarioServicio = usuarioServicio;
	}

	@Override
	public Tareas guardarTarea(Tareas tarea) {
		// Guardar la tarea en la base de datos y devolver la tarea guardada
		return tareasRepositorio.save(tarea);
	}

	@Override
	public List<Tareas> listarTareas() {
		// Obtener todas las tareas almacenadas en la base de datos
		return tareasRepositorio.findAll();
	}

	@Override
	public List<Tareas> listarTareasPorUsuario(String nombreUsuario) {
		// Aquí deberías obtener el Usuario basado en su nombre (por ejemplo, usando un
		// servicio de Usuario)
		// Supongamos que obtienes el usuario de alguna manera (por ejemplo, un servicio
		// de usuarios)
		Usuario usuario = usuarioServicio.obtenerUsuarioPorNombre(nombreUsuario);

		if (usuario != null) {
			return tareasRepositorio.findByUsuario(usuario);
		} else {
			// Manejar el caso cuando no se encuentra el usuario
			return Collections.emptyList(); // o null, dependiendo de tu lógica
		}
	}

	@Override
	public Tareas obtenerTareaCodigo(String codigo) {
		// Buscar la tarea por su código en la base de datos
		return tareasRepositorio.findByCodigo(codigo);
	}

	@Override
	public Tareas obtenerTareaPorId(Long id) {
		// Buscar la tarea por su ID en la base de datos
		return tareasRepositorio.findById(id).orElse(null);
	}

	@Override
	public List<Tareas> listarTareasPorIds(List<Long> tareaIds) {
		return tareasRepositorio.findAllById(tareaIds);
	}
}
