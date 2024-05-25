package com.registro.controlador;

import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.registro.modelo.Equipo;
import com.registro.modelo.Usuario;
import com.registro.servicio.EquipoServicio;
import com.registro.servicio.UsuarioServicio;

@Controller
public class RegistroControlador {

    @Autowired
    private UsuarioServicio servicio;
    @Autowired
    private EquipoServicio equipoServicio;
    
    @GetMapping("/login")
    public String iniciarSesion() {
        return "login";
    }
    
    @PostMapping("/login")
    public String autenticarUsuario(Model model, HttpServletRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
            
            // Itera sobre las autoridades para imprimir los roles del usuario
            System.out.println("El usuario " + userDetails.getUsername() + " tiene los siguientes roles:");
            for (GrantedAuthority authority : authorities) {
                System.out.println(authority.getAuthority());
            }
            
            if (authorities.contains(new SimpleGrantedAuthority("[supervisor]"))) {
                // Si el usuario es un supervisor, redirigir a la página del supervisor
                return "redirect:/";
            } else if (authorities.contains(new SimpleGrantedAuthority("[trabajador]"))) {
                // Si el usuario es un trabajador, redirigir a la página del trabajador
                return "redirect:/tareas"; // Cambiar esto a "redirect:/trabajador"
            } else {
                // Manejar otros roles según sea necesario
                return "redirect:/otra-pagina";
            }
        } else {
            // Manejar la autenticación fallida
            model.addAttribute("error", true);
            return "login";
        }
    }

    
    @GetMapping("/")
    public String verPaginaDeInicio(Model modelo, @AuthenticationPrincipal UserDetails principal) {
    	 // Obtener el nombre de usuario del contexto de seguridad
    	String nombreUsuario = principal.getUsername();
        System.out.println(nombreUsuario);
        // Obtener el objeto Usuario completo por nombre de usuario
        Long entrenador = servicio.obtenerIdPorEmail(nombreUsuario);
        System.out.println(entrenador);
        
        List<Equipo> equipos = equipoServicio.buscarPorIdUsuario(entrenador);
        for (Equipo equipo : equipos){
        	System.out.println(equipo.getNombreEquipo() +" "+ equipo.getCategoria());
        }
        modelo.addAttribute("equipos", equipos);
        
        modelo.addAttribute("usuarios", servicio.listarUsuarios());
        return "index";
    }
}
