package com.registro.controlador;

import com.registro.modelo.Equipo;
import com.registro.modelo.Tareas;
import com.registro.modelo.Usuario;
import com.registro.servicio.EquipoServicio;
import com.registro.servicio.JugadorServicio;
import com.registro.servicio.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/equipo")
public class EquipoControlador {

	@Autowired
	private EquipoServicio equipoService;

	@Autowired
	private UsuarioServicio usuarioService;
	
	@Autowired
	private JugadorServicio jugadoresServicio;

	@GetMapping("/equipos")
	public String mostrarEquipos(Model model) {
		List<Equipo> equipos = equipoService.buscarPorNombreEquipo(""); // Obtener todos los equipos
		model.addAttribute("equipos", equipos);
		return "equipos"; // Nombre de la vista para mostrar equipos
	}

	// Mostrar formulario para crear una nueva tarea
	@GetMapping("/nuevo")
	public String mostrarFormularioEquipo(Model model) {
		model.addAttribute("equipo", new Equipo());
		return "formularioEquipo";
	}

	@PostMapping("/guardar")
	public String guardarEquipo(@ModelAttribute("equipo") Equipo equipo, Principal principal,
			RedirectAttributes redirectAttributes) {
		// Obtener el nombre de usuario del contexto de seguridad
		String nombreUsuario = principal.getName();
		System.out.println(nombreUsuario);
		// Obtener el objeto Usuario completo por nombre de usuario
		Usuario entrenador = usuarioService.obtenerUsuarioPorNombre(nombreUsuario);
		System.out.println(entrenador);
		// Asignar el entrenador al equipo
		equipo.setEntrenador(entrenador);

		// Guardar el equipo en la base de datos
		Equipo equipoGuardado = equipoService.guardarEquipo(equipo);
		System.out.println(equipoGuardado);
		// Redirigir a la vista de detalle del equipo guardado
		redirectAttributes.addAttribute("id", equipoGuardado.getId());
		return "redirect:/jugadores/nuevo";
	}
	
	@GetMapping("/{id}")
	public String mostrarDetalleEquipo(@PathVariable Long id, Model model) {
	    Equipo equipo = equipoService.obtenerEquipoPorId(id);
	    if (equipo != null) {
	        model.addAttribute("equipo", equipo);
	        model.addAttribute("jugadores", jugadoresServicio.obtenerJugadoresPorEquipo(equipo)); 
	        return "equipoDetalle"; 
	    } else {
	        return "redirect:/";
	    }
	}

}
