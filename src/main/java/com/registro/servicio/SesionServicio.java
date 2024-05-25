package com.registro.servicio;

import java.util.List;

import com.registro.modelo.Sesion;
import com.registro.modelo.Tareas;

public interface SesionServicio {
    
    Sesion guardarSesion(Sesion sesion);
    
    List<Sesion> listarSesiones();
    
    Sesion obtenerSesionPorId(Long id);
    
    List<Sesion> listarSesionesPorUsuario(Long usuarioId);
    
    List<Tareas> obtenerTareasPorSesionId(Long sesionId);
}
