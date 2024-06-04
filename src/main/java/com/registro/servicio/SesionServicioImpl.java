package com.registro.servicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.registro.modelo.Sesion;
import com.registro.modelo.Tareas;
import com.registro.modelo.Usuario;
import com.registro.repositorio.SesionRepositorio;
import com.registro.repositorio.TareasRepositorio;

@Service
public class SesionServicioImpl implements SesionServicio {

    private final SesionRepositorio sesionRepositorio;
    private final UsuarioServicio usuarioServicio;
    private final TareasRepositorio tareasRepoistorio;

    @Autowired
    public SesionServicioImpl(SesionRepositorio sesionRepositorio, UsuarioServicio usuarioServicio, TareasRepositorio tareasRepoistorio) {
        this.sesionRepositorio = sesionRepositorio;
        this.usuarioServicio = usuarioServicio;
        this.tareasRepoistorio = tareasRepoistorio;
    }

    @Override
    public Sesion guardarSesion(Sesion sesion) {
        // Guardar la sesión en la base de datos y devolver la sesión guardada
        return sesionRepositorio.save(sesion);
    }

    @Override
    public List<Sesion> listarSesiones() {
        // Obtener todas las sesiones almacenadas en la base de datos
        return sesionRepositorio.findAll();
    }

    @Override
    public Sesion obtenerSesionPorId(Long id) {
        // Buscar la sesión por su ID en la base de datos
        return sesionRepositorio.findById(id).orElse(null);
    }

    @Override
    public List<Sesion> listarSesionesPorUsuario(Long usuarioId) {
        // Buscar las sesiones por el ID del usuario
        return sesionRepositorio.findByUsuarioId(usuarioId);
    }
    
    @Override
    public List<Tareas> obtenerTareasPorSesionId(Long sesionId) {
        List<Long> tareaIds = sesionRepositorio.findTareaIdsBySesionId(sesionId);
        // Suponiendo que tienes un repositorio o servicio para Tareas para obtener las entidades Tareas por sus IDs
        return tareasRepoistorio.findAllById(tareaIds);
    }
    
    @Override
    public void eliminarSesion(Long id) {
        sesionRepositorio.deleteById(id);
    }
}
