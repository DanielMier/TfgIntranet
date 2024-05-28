package com.registro.controlador;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.registro.modelo.Tareas;
import com.registro.modelo.Usuario;
import com.registro.repositorio.TareasRepositorio;
import com.registro.servicio.TareasServicio;
import com.registro.servicio.UsuarioServicio;

@Controller
@RequestMapping("/tareas")
public class TareaControlador {

    @Autowired
    private TareasServicio tareasServicio;
    @Autowired
    private TareasRepositorio tareasRepositorio;

    @Autowired
    private UsuarioServicio usuarioServicio;

    @Value("${upload.path}")
    private String uploadPath;

    @GetMapping("/{codigo}")
    public String mostrarDetalleTarea(@PathVariable String codigo, Model model) {
        Tareas tarea = tareasServicio.obtenerTareaCodigo(codigo);

        if (tarea == null) {
            return "error"; // Plantilla de error personalizada
        }

        model.addAttribute("tarea", tarea);
        return "tareaDetalle"; // Devuelve el nombre de la plantilla Thymeleaf
    }

    @GetMapping("/nueva")
    public String mostrarFormularioNuevaTarea(Model model) {
        model.addAttribute("tarea", new Tareas());
        return "formularioTarea"; // Vista del formulario para crear tarea
    }

    @PostMapping("/guardar")
    public String guardarTarea(
        @Valid @ModelAttribute("tarea") Tareas tarea,
        BindingResult bindingResult,
        @RequestParam("file") MultipartFile file,
        Principal principal,
        RedirectAttributes redirectAttributes
    ) {
        if (bindingResult.hasErrors()) {
            return "formularioTarea"; // Nombre correcto de tu vista de formulario
        }
        
        // Obtener el nombre de usuario del contexto de seguridad
        String nombreUsuario = principal.getName();
        // Obtener el objeto Usuario completo por nombre de usuario
        Usuario usuario = usuarioServicio.obtenerUsuarioPorNombre(nombreUsuario);
        // Asignar el usuario a la tarea
        tarea.setUsuarioId(usuario);

        // Guardar el archivo en el servidor
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                // Define el directorio donde se guardarán los archivos
                String directorio = uploadPath;
                // Asegúrate de que el directorio existe
                File directorioDestino = new File(directorio);
                if (!directorioDestino.exists()) {
                    directorioDestino.mkdirs();
                }
                // Guardar el archivo con un nombre único
                String nombreArchivo = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
                Path rutaArchivo = Paths.get(directorio, nombreArchivo);
                Files.copy(file.getInputStream(), rutaArchivo, StandardCopyOption.REPLACE_EXISTING);
                // Establecer el nombre del archivo en la entidad Tareas
                tarea.setVideoImagen(nombreArchivo);
            } catch (IOException e) {
                e.printStackTrace();
                // Manejo de errores si es necesario
                redirectAttributes.addFlashAttribute("error", "Error al subir el archivo: " + e.getMessage());
                return "redirect:/tareas/nueva";
            }
        }
        // Guardar la tarea en la base de datos
        Tareas tareaGuardada = tareasServicio.guardarTarea(tarea);
        // Redirigir a la vista de detalle de la tarea guardada
        redirectAttributes.addAttribute("codigo", tareaGuardada.getCodigo());
        return "redirect:/tareas/{codigo}";
    }


    @GetMapping("/usuarios")
    public String mostrarTareasUsuario(Model model, Principal principal) {
        String nombreUsuario = principal.getName(); // Obtenemos el nombre de usuario del contexto de seguridad
        List<Tareas> tareasUsuario = tareasServicio.listarTareasPorUsuario(nombreUsuario);
        model.addAttribute("tareas", tareasUsuario);
        return "listaTareasUsuario"; // Vista para mostrar la lista de tareas del usuario
    }
    
    @GetMapping
    public String mostrarTareas(Model model, Principal principal) {
        List<Tareas> tareasUsuario = tareasServicio.listarTareas();
        model.addAttribute("tareas", tareasUsuario);
        return "listaTareas"; // Vista para mostrar la lista de tareas del usuario
    }
   
}
