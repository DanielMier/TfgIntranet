package com.registro.servicio;

import com.registro.modelo.Equipo;
import com.registro.modelo.Usuario;
import com.registro.repositorio.EquipoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EquipoServicioImpl implements EquipoServicio {

    @Autowired
    private EquipoRepositorio equipoRepository;

    @Override
    public List<Equipo> buscarPorNombreEquipo(String nombreEquipo) {
        return equipoRepository.findByNombreEquipo(nombreEquipo);
    }

    @Override
    public List<Equipo> buscarPorIdUsuario(Long idUsuario) {
        return equipoRepository.findByEntrenadorId(idUsuario);
    }
    @Override
    public List<Equipo> listarPorUsuario(Usuario usuario) {
        return equipoRepository.findByEntrenador(usuario);
    }
    @Override
    public Equipo buscarPorIdYIdUsuario(Long idEquipo, Long idUsuario) {
        return equipoRepository.findByIdAndEntrenadorId(idEquipo, idUsuario);
    }
    
    @Override
    public Equipo guardarEquipo(Equipo equipo) {
        return equipoRepository.save(equipo);
    }

    @Override
    public Equipo obtenerEquipoPorId(Long id) {
        return equipoRepository.findById(id).orElse(null);
    }
}
